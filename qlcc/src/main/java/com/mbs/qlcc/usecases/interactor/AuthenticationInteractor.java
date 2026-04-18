package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Complex.IComplexFactory;
import com.mbs.qlcc.entities.User.IUserFactory;
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
import com.mbs.qlcc.utils.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
//        var user = userDsGateway.findByUsernameAndComplexId(authenticationInpRequest.getUsername(), authenticationInpRequest.getComplexId());
//
//        if (user == null) {
//            throw new AppException(ErrorCode.USER_NON_EXISTED);
//        }
//
//        boolean authenticated = tokenDsGateway.matches(authenticationInpRequest.getPasswordRaw(), user.getPassword());
//
//        if (!authenticated) {
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }

//        var orgUser = orgUserDsGateway.findUserByOrgId(user.getId(), authenticationInpRequest.getOrgId());

//        if (orgUser == null) {
//            throw new AppException(ErrorCode.INCORRECT_LOGIN_INFO);
//        }
//
//        var permissions = rolePermissionDsGateway.findPermissionByRoleId(orgUser.getRole_id());
//        List<String> listPermission = permissions.stream().map(Permission::getName).toList();
        List<String> listPermission = new ArrayList<>();
        listPermission.add("READ_USER");
        listPermission.add("CREATE_USER");

//        Map<String, String> claims = Map.of("complex_id", user.getComplex_id(), "org_id", orgUser.getOrg_id(), "user_id", user.getId());
        Map<String, String> claims = Map.of("complex_id", "1", "org_id", "2", "user_id", "3");


        var accessToken = tokenDsGateway.generateAccessToken(claims, listPermission, 0);
//        var refreshToken = tokenService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, "a", true);
    }

    @Override
    public void logout(LogoutInpRequest logoutInpRequest) {

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenInpRequest refreshTokenInpRequest) {
        return null;
    }

}
