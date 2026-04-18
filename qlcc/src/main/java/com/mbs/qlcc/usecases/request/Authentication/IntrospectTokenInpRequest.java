package com.mbs.qlcc.usecases.request.Authentication;

public class IntrospectTokenInpRequest {
    String token;

    public IntrospectTokenInpRequest() {
    }

    public IntrospectTokenInpRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
