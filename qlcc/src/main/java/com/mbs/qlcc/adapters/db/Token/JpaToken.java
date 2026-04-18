package com.mbs.qlcc.adapters.db.Token;

import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.output.User.ITokenDsGateway;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.usecases.response.User.TokenResponse;
import com.mbs.qlcc.utils.ErrorCode;
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
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JpaToken implements ITokenDsGateway {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.validDuration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshableDuration}")
    protected long REFRESHABLE_DURATION;

    final JpaTokenRepository repository;
    final PasswordEncoder encoder;

    @Override
    public TokenResponse findByRefreshToken(String refreshToken) {
        var token = repository.findByRefreshToken(refreshToken);
        TokenResponse tokenRefreshResponse = new TokenResponse(token.token, token.expirationDate, token.refreshToken,
                token.refreshExpirationDate, token.revoked, token.expired
        );
        return tokenRefreshResponse;
    }

    @Override
    public TokenResponse findByToken(String token) {
        var tokenRes = repository.findByToken(token);
        TokenResponse tokenResponse = new TokenResponse(tokenRes.token, tokenRes.expirationDate, tokenRes.refreshToken,
                tokenRes.refreshExpirationDate, tokenRes.revoked, tokenRes.expired
        );
        return tokenResponse;
    }

    @Override
    public boolean introspect(IntrospectTokenInpRequest introspectTokenInpRequest) {
        var token = introspectTokenInpRequest.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false); // kiem tra token hop le hay k va false=> de lay expiry cua token
        } catch (JOSEException | ParseException e) {
            isValid = false;
            throw new RuntimeException(e);
        } catch (AppException e) {
            isValid = false;
            throw e;
        }

        //neu hop le
        return isValid;
    }

    @Override
    public boolean matches(String raw, String hash) {
        return encoder.matches(raw, hash);
    }

    @Override
    public String hash(String raw) {
        return encoder.encode(raw);
    }

    //type 0: access token; type 1: refresh token
    @Override
    public String generateAccessToken(Map<String, String> claims, List<String> permissions, int type) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        var expirationTime = type == 0 ? VALID_DURATION : REFRESHABLE_DURATION;

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(claims.get("user_id"))
                .issuer("atus")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(expirationTime, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(permissions))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private TokenDataMapper verifyToken(String token, boolean typeToken) throws JOSEException, ParseException, AppException {
        //tao khoa chua chữ ký của token.
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        //Phân tích chuỗi token thành đối tượng SignedJWT để có thể truy cập các thuộc tính của nó
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        // xac minh chu ki
        var verified = signedJWT.verify(verifier);
        var existingToken = !typeToken ? repository.findByToken(token) : repository.findByRefreshToken(token);
        // chu ki k hop le hoac het han token
        // kien tra su ton tai cua token trong db vi token co the hop le nhung k con luu trong db
//        if (!(verified && expiryTime.after(new Date()) && existingToken !=null)) throw new AppException(ErrorCode.UNAUTHENTICATED);
        if (!verified || existingToken == null) throw new AppException(ErrorCode.TOKEN_INVALID);
        if (expiryTime.before(new Date())) throw new AppException(ErrorCode.TOKEN_EXPIRED);

        // co the coi la token dc convert sang thanh 1 obj
        // token hop le con han se dc return
        return existingToken;
    }


    private String buildScope(List<String> permissions) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(permissions))
            permissions.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }


}
