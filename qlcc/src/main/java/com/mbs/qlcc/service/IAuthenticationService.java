package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Authentication.AuthenticationRequest;
import com.mbs.qlcc.dto.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.dto.response.Authentication.IntrospectResponse;
import com.mbs.qlcc.dto.response.Authentication.ProfileResponse;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
    IntrospectResponse introspect(String token);
    String logout(String refreshToken);
    AuthenticationResponse refreshToken(String refreshToken);
    ProfileResponse profile(String token);
}
