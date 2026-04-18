package com.mbs.qlcc.usecases.response.Authentication;

public class IntrospectResponse {
    boolean resValid;

    public IntrospectResponse() {
    }

    public IntrospectResponse(boolean resValid) {
        this.resValid = resValid;
    }

    public boolean isResValid() {
        return resValid;
    }

    public void setResValid(boolean resValid) {
        this.resValid = resValid;
    }
}
