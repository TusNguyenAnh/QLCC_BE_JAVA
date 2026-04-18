package com.mbs.qlcc.usecases.request.Authentication;

public class RefreshTokenInpRequest {
    String token;

    public RefreshTokenInpRequest() {
    }

    public RefreshTokenInpRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
