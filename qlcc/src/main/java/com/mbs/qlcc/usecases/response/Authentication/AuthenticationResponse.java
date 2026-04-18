package com.mbs.qlcc.usecases.response.Authentication;

public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
    boolean authenticated;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String accessToken, String refreshToken, boolean authenticated) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.authenticated = authenticated;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
