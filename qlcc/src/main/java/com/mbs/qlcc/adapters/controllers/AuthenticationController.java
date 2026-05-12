package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Authentication.AuthenticationRequest;
import com.mbs.qlcc.adapters.request.Authentication.LogoutRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.AuthenticationService;
import com.mbs.qlcc.usecases.request.Authentication.AuthenticationInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.LogoutInpRequest;
import com.mbs.qlcc.usecases.request.Authentication.RefreshTokenInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.usecases.response.Authentication.ProfileResponse;
import com.mbs.qlcc.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService service;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest req,
                                                     HttpServletResponse response
    ) {

        AuthenticationResponse authenticationResponse = service.login(new AuthenticationInpRequest(req.getUsername(), req.getPasswordRaw(), req.getComplexId(), req.getOrgId()));
        ResponseCookie cookie = ResponseCookie.from("refreshToken", authenticationResponse.getRefreshToken())
                .httpOnly(true)
//                .secure(true) // Chỉ gửi cookie qua kết nối HTTPS // , neu secure true thi sameSite None moi co hieu luc
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Lax") // Hoặc "Strict" tùy theo yêu cầu bảo mật của bạn
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        String logoutResult = service.logout(new LogoutInpRequest(refreshToken));
        ResponseCookie cookie =
                ResponseCookie.from("refreshToken", "")
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(0)
                        .sameSite("Lax")
                        .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ApiResponse.<String>builder()
                .result(logoutResult)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(service.refreshToken(
                        new RefreshTokenInpRequest(refreshToken)
                ))
                .build();
    }

    @PostMapping("/profile")
    public ApiResponse<ProfileResponse> profile() {
        return ApiResponse.<ProfileResponse>builder()
                .result(service.profile(JwtUtil.getToken()))
                .build();
    }
}