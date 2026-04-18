package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Authentication.AuthenticationInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.LogoutInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.RefreshTokenInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.usecases.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.usecases.response.Authentication.TokenResponse;

public interface IAuthenticationInputBoundary {
    public IntrospectResponse introspect(IntrospectTokenInpRequest introspectTokenInpRequest);
    public AuthenticationResponse login(AuthenticationInpRequest authenticationInpRequest);
    public void logout(LogoutInpRequest logoutInpRequest);
    public AuthenticationResponse refreshToken(RefreshTokenInpRequest refreshTokenInpRequest);
//    public TokenResponse addToken(User user, String token, String refreshToken);
    }
