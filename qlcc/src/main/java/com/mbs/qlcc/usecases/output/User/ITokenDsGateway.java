package com.mbs.qlcc.usecases.output.User;

import com.mbs.qlcc.adapters.db.Token.TokenDataMapper;
import com.mbs.qlcc.adapters.db.User.UserDataMapper;
import com.mbs.qlcc.entities.User.Token;
import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.usecases.response.User.TokenResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


public interface ITokenDsGateway {
    Token findByRefreshToken(String refreshToken);
    Token findByToken(String token);
    boolean introspect(IntrospectTokenInpRequest introspectTokenInpRequest);
    boolean matches(String raw, String hash);
    String hash(String raw);
    String generateToken(Map<String,String> claims, List<String> permissions,int type);
    public Token verifyToken(String token, boolean typeToken);
    boolean revokeToken(String token);
    public Token addToken(User user, String token, String refreshToken);
    }
