package com.mbs.qlcc.usecases.output.User;

import com.mbs.qlcc.adapters.db.User.UserDataMapper;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.usecases.response.User.TokenResponse;

import java.util.List;
import java.util.Map;


public interface ITokenDsGateway {
    TokenResponse findByRefreshToken(String refreshToken);

    TokenResponse findByToken(String token);

    boolean introspect(IntrospectTokenInpRequest introspectTokenInpRequest);

    boolean matches(String raw, String hash);
    String hash(String raw);
    String generateAccessToken(Map<String,String> claims, List<String> permissions,int type);
//    String generateRefreshToken(UserInpRequest user);
}
