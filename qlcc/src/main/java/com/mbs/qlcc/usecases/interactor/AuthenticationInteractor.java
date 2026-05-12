package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Complex.IComplexFactory;
import com.mbs.qlcc.entities.User.IUserFactory;
import com.mbs.qlcc.entities.User.Token;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IAuthenticationInputBoundary;
import com.mbs.qlcc.usecases.output.Authentication.IRolePermissionDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.User.ITokenDsGateway;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.Authentication.AuthenticationInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.LogoutInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.RefreshTokenInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.usecases.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.usecases.response.Authentication.ProfileResponse;
import com.mbs.qlcc.usecases.response.User.UserResponse;
import com.mbs.qlcc.utils.ErrorCode;
import com.mbs.qlcc.utils.JwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthenticationInteractor implements IAuthenticationInputBoundary {
    protected String SIGNER_KEY;
    protected long VALID_DURATION;
    protected long REFRESHABLE_DURATION;
    IUserDsGateway userDsGateway;
    ITokenDsGateway tokenDsGateway;
    IUserFactory userFactory;
    IComplexFactory complexFactory;
    IOrgUserDsGateway orgUserDsGateway;
    IRolePermissionDsGateway rolePermissionDsGateway;

    public AuthenticationInteractor(String SIGNER_KEY, long VALID_DURATION, long REFRESHABLE_DURATION, IUserDsGateway userDsGateway, ITokenDsGateway tokenDsGateway, IUserFactory userFactory, IComplexFactory complexFactory, IOrgUserDsGateway orgUserDsGateway, IRolePermissionDsGateway rolePermissionDsGateway) {
        this.SIGNER_KEY = SIGNER_KEY;
        this.VALID_DURATION = VALID_DURATION;
        this.REFRESHABLE_DURATION = REFRESHABLE_DURATION;
        this.userDsGateway = userDsGateway;
        this.tokenDsGateway = tokenDsGateway;
        this.userFactory = userFactory;
        this.complexFactory = complexFactory;
        this.orgUserDsGateway = orgUserDsGateway;
        this.rolePermissionDsGateway = rolePermissionDsGateway;
    }

    @Override
    public IntrospectResponse introspect(IntrospectTokenInpRequest introspectTokenInpRequest) {
        boolean isValid = tokenDsGateway.introspect(introspectTokenInpRequest);
        //neu hop le
        return new IntrospectResponse(isValid);

    }

    @Override
    public AuthenticationResponse login(AuthenticationInpRequest authenticationInpRequest) {
        var user = userDsGateway.findByUsernameAndComplexId(authenticationInpRequest.getUsername(), authenticationInpRequest.getComplexId());

        if (user == null) {
            throw new AppException(ErrorCode.USER_NON_EXISTED);
        }

        boolean authenticated = tokenDsGateway.matches(authenticationInpRequest.getPasswordRaw(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var orgUser = orgUserDsGateway.findUserByOrgId(user.getId(), authenticationInpRequest.getOrgId());

        if (orgUser == null) {
            throw new AppException(ErrorCode.INCORRECT_LOGIN_INFO);
        }

        var permissions = rolePermissionDsGateway.findPermissionByRoleId(orgUser.getRole_id());
        List<String> listPermission = permissions.stream().map(Permission::getName).toList();

        Map<String, String> claims = Map.of("complex_id", user.getComplex_id(), "org_id", orgUser.getOrg_id(), "user_id", user.getId());

        var accessToken = tokenDsGateway.generateToken(claims, listPermission, 0);
        var refreshToken = tokenDsGateway.generateToken(claims, new ArrayList<>(), 1);

        Token newToken = tokenDsGateway.addToken(user, accessToken, refreshToken);

        return new AuthenticationResponse(newToken.getToken(), newToken.getRefreshToken(), true, "Đăng nhập thành công!");
    }

    @Override
    public String logout(LogoutInpRequest logoutInpRequest) {
        Token existingToken = tokenDsGateway.findByRefreshToken(logoutInpRequest.getToken());
        if (existingToken == null) throw new AppException(ErrorCode.TOKEN_INVALID);
        tokenDsGateway.revokeToken(existingToken.getId());
        return "Logout successfully!";
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenInpRequest refreshTokenInpRequest) {
        // kiem tra token hop le:  dung chu ki va con thoi gian song
        Token existingToken = null;
        try {
            existingToken = tokenDsGateway.verifyToken(refreshTokenInpRequest.getToken(), true);
            tokenDsGateway.revokeToken(existingToken.getId());
        } catch (AppException e) {
            if (e.getErrorCode().getCode() == 1005) throw new AppException(ErrorCode.INCORRECT_RF_TOKEN);
            if (e.getErrorCode().getCode() == 1002) throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        var user = userDsGateway.findById(JwtUtil.getClaim(refreshTokenInpRequest.getToken()).get("sub").toString());

        if (user == null) {
            throw new AppException(ErrorCode.USER_NON_EXISTED);
        }

        var orgUser = orgUserDsGateway.findUserByOrgId(user.getId(), JwtUtil.getClaim(refreshTokenInpRequest.getToken()).get("org_id").toString());

        if (orgUser == null) {
            throw new AppException(ErrorCode.INCORRECT_LOGIN_INFO);
        }

        var permissions = rolePermissionDsGateway.findPermissionByRoleId(orgUser.getRole_id());
        List<String> listPermission = permissions.stream().map(Permission::getName).toList();

        Map<String, String> claims = Map.of("complex_id", user.getComplex_id(), "org_id", orgUser.getOrg_id(), "user_id", user.getId());

        var accessToken = tokenDsGateway.generateToken(claims, listPermission, 0);
        var refreshToken = tokenDsGateway.generateToken(claims, new ArrayList<>(), 1);

        Token newToken = tokenDsGateway.addToken(user, accessToken, refreshToken);
        return new AuthenticationResponse(newToken.getToken(), newToken.getRefreshToken(), true, "Refresh thành công!");
    }

    @Override
    public ProfileResponse profile(String token) {
        Map<String, Object> claims = JwtUtil.getClaim(token);
        String orgId = claims.get("org_id").toString();
        String permissions = claims.get("scope").toString();
        String userId = claims.get("sub").toString();
        var user = userDsGateway.findById(userId);

        if (user == null) {
            throw new AppException(ErrorCode.USER_NON_EXISTED);
        }
        UserResponse userResponse = new UserResponse(user.getUsername(), user.getRes_id(),user.getComplex_id(), user.isStatus());

        return new ProfileResponse(orgId, userResponse, permissions);
    }


}
