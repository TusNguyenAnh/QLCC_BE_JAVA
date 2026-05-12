package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Authentication.AuthenticationRequest;
import com.mbs.qlcc.usecases.input.IAuthenticationInputBoundary;
import com.mbs.qlcc.usecases.request.Authentication.AuthenticationInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.LogoutInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.RefreshTokenInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.usecases.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.usecases.response.Authentication.ProfileResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    IAuthenticationInputBoundary useCase;

    @Transactional
    public AuthenticationResponse login(AuthenticationInpRequest authenticationRequest) {
        return useCase.login(authenticationRequest);
    }

    public IntrospectResponse introspect(IntrospectTokenInpRequest introspectTokenInpRequest) {
        return useCase.introspect(introspectTokenInpRequest);
    }

    public String logout(LogoutInpRequest logoutInpRequest) {
        return useCase.logout(logoutInpRequest);
    }

    public AuthenticationResponse refreshToken(RefreshTokenInpRequest refreshTokenInpRequest) {
        return useCase.refreshToken(refreshTokenInpRequest);
    }

    public ProfileResponse profile(String token) {
        return useCase.profile(token);
    }
}
