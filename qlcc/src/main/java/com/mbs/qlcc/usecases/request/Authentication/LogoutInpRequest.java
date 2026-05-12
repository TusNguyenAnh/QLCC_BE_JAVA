package com.mbs.qlcc.usecases.request.Authentication;


public class LogoutInpRequest {
    String token;

    public LogoutInpRequest() {
    }

    public LogoutInpRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
