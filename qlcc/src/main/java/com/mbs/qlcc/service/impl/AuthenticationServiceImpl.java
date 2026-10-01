package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.Permission;
import com.mbs.qlcc.domain.Token;
import com.mbs.qlcc.domain.User;
import com.mbs.qlcc.dto.request.Authentication.AuthenticationRequest;
import com.mbs.qlcc.dto.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.dto.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.dto.response.Authentication.ProfileResponse;
import com.mbs.qlcc.dto.response.User.UserResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.repository.Authentication.IRolePermissionRepository;
import com.mbs.qlcc.repository.Authentication.ITokenRepository;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.User.IUserRepository;
import com.mbs.qlcc.service.IAuthenticationService;
import com.mbs.qlcc.utils.ErrorCode;
import com.mbs.qlcc.utils.JwtUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {

    IUserRepository userRepository;
    IOrgUserRepository orgUserRepository;
    IRolePermissionRepository rolePermissionRepository;
    ITokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String signerKey;

    @NonFinal
    @Value("${jwt.validDuration}")
    long validDuration;

    @NonFinal
    @Value("${jwt.refreshableDuration}")
    long refreshableDuration;

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findByUsernameAndComplexId(request.getUsername(), request.getComplexId());
        if (user == null) {
            throw new AppException(ErrorCode.USER_NON_EXISTED);
        }

        boolean authenticated = passwordEncoder.matches(request.getPasswordRaw(), user.getPasswordHash());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        OrgUser orgUser = orgUserRepository.findByUserIdAndOrgId(user.getId(), request.getOrgId());
        if (orgUser == null) {
            throw new AppException(ErrorCode.INCORRECT_LOGIN_INFO);
        }

        List<String> permissions = rolePermissionRepository.findPermissionsByRoleId(orgUser.getRoleId())
                .stream()
                .map(Permission::getName)
                .toList();

        Map<String, String> claims = Map.of(
                "complex_id", user.getComplexId(),
                "org_id", orgUser.getOrgId(),
                "user_id", user.getId()
        );

        String accessToken = generateToken(claims, permissions, 0);
        String refreshToken = generateToken(claims, Collections.emptyList(), 1);

        saveToken(user.getId(), accessToken, refreshToken);

        return new AuthenticationResponse(accessToken, refreshToken, true, "Đăng nhập thành công!");
    }

    @Override
    public IntrospectResponse introspect(String token) {
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
            throw e;
        }
        return new IntrospectResponse(isValid);
    }

    @Override
    @Transactional
    public String logout(String refreshToken) {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        if (existingToken == null) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        tokenRepository.deleteById(existingToken.getId());
        return "Logout successfully!";
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken) {
        Token existingToken;
        try {
            existingToken = verifyToken(refreshToken, true);
            tokenRepository.deleteById(existingToken.getId());
        } catch (AppException e) {
            if (e.getErrorCode().getCode() == 1005) throw new AppException(ErrorCode.INCORRECT_RF_TOKEN);
            if (e.getErrorCode().getCode() == 1002) throw new AppException(ErrorCode.TOKEN_EXPIRED);
            throw e;
        }

        Map<String, Object> jwtClaims = JwtUtil.getClaim(refreshToken);
        String userId = jwtClaims.get("sub").toString();
        String orgId = jwtClaims.get("org_id").toString();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NON_EXISTED);
        }

        OrgUser orgUser = orgUserRepository.findByUserIdAndOrgId(userId, orgId);
        if (orgUser == null) {
            throw new AppException(ErrorCode.INCORRECT_LOGIN_INFO);
        }

        List<String> permissions = rolePermissionRepository.findPermissionsByRoleId(orgUser.getRoleId())
                .stream()
                .map(Permission::getName)
                .toList();

        Map<String, String> claims = Map.of(
                "complex_id", user.getComplexId(),
                "org_id", orgUser.getOrgId(),
                "user_id", user.getId()
        );

        String newAccessToken = generateToken(claims, permissions, 0);
        String newRefreshToken = generateToken(claims, Collections.emptyList(), 1);

        saveToken(user.getId(), newAccessToken, newRefreshToken);

        return new AuthenticationResponse(newAccessToken, newRefreshToken, true, "Refresh thành công!");
    }

    @Override
    public ProfileResponse profile(String token) {
        Map<String, Object> claims = JwtUtil.getClaim(token);
        String orgId = claims.get("org_id").toString();
        String permissions = claims.get("scope").toString();
        String userId = claims.get("sub").toString();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NON_EXISTED);
        }

        UserResponse userResponse = new UserResponse(
                user.getUsername(),
                user.getResId(),
                user.getComplexId(),
                !user.isDeleted()
        );

        return new ProfileResponse(orgId, userResponse, permissions);
    }


    /** type=0: access token; type=1: refresh token */
    private String generateToken(Map<String, String> claims, List<String> permissions, int type) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        long durationSeconds = (type == 0) ? validDuration : refreshableDuration;

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(claims.get("user_id"))
                .issuer("atus")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(durationSeconds, ChronoUnit.SECONDS)))
                .jwtID(UUID.randomUUID().toString())
                .claim("complex_id", claims.get("complex_id"))
                .claim("org_id", claims.get("org_id"))
                .claim("scope", buildScope(permissions))
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    private Token verifyToken(String rawToken, boolean isRefreshToken) {
        try {
                        //tao khoa chua chữ ký của token.
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
                        //Phân tích chuỗi token thành đối tượng SignedJWT để có thể truy cập các thuộc tính của nó
            SignedJWT signedJWT = SignedJWT.parse(rawToken);

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
                        // xac minh chu ki
            boolean verified = signedJWT.verify(verifier);

            Token token = isRefreshToken
                    ? tokenRepository.findByRefreshToken(rawToken)
                    : tokenRepository.findByToken(rawToken);
                    
   // chu ki k hop le hoac het han token
            // kien tra su ton tai cua token trong db vi token co the hop le nhung k con luu trong db
            if (!verified || token == null) throw new AppException(ErrorCode.TOKEN_INVALID);
            if (expiryTime.before(new Date())) throw new AppException(ErrorCode.TOKEN_EXPIRED);

            return token;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("Failed to verify JWT token", e);
        }
    }

    private void saveToken(String userId, String accessToken, String refreshToken) {
        try {
            SignedJWT parsedAccess = SignedJWT.parse(accessToken);
            SignedJWT parsedRefresh = SignedJWT.parse(refreshToken);

            Token token = Token.builder()
                    .userId(userId)
                    .token(accessToken)
                    .refreshToken(refreshToken)
                    .expirationDate(parsedAccess.getJWTClaimsSet().getExpirationTime())
                    .refreshExpirationDate(parsedRefresh.getJWTClaimsSet().getExpirationTime())
                    .revoked(false)
                    .expired(false)
                    .build();

            tokenRepository.save(token);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to save token", e);
        }
    }

    private String buildScope(List<String> permissions) {
        if (CollectionUtils.isEmpty(permissions)) return "";
        StringJoiner joiner = new StringJoiner(" ");
        permissions.forEach(joiner::add);
        return joiner.toString();
    }
}
