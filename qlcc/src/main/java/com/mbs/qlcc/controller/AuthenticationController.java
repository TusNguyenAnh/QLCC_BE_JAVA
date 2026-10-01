package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Authentication.AuthenticationRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.Authentication.AuthenticationResponse;
import com.mbs.qlcc.dto.response.Authentication.ProfileResponse;
import com.mbs.qlcc.service.IAuthenticationService;
import com.mbs.qlcc.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse result = authenticationService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        String logoutResult = authenticationService.logout(refreshToken);

        ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());

        return ApiResponse.<String>builder()
                .result(logoutResult)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(refreshToken))
                .build();
    }

    @PostMapping("/profile")
    public ApiResponse<ProfileResponse> profile() {
        return ApiResponse.<ProfileResponse>builder()
                .result(authenticationService.profile(JwtUtil.getToken()))
                .build();
    }
}