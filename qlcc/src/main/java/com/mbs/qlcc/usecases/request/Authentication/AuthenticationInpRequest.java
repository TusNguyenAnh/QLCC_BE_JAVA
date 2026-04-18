package com.mbs.qlcc.usecases.request.Authentication;

public class AuthenticationInpRequest {
    String username;
    String passwordRaw;
    String complexId;
    String orgId;

    public AuthenticationInpRequest() {
    }

    public AuthenticationInpRequest(String username, String passwordRaw, String complexId, String orgId) {
        this.username = username;
        this.passwordRaw = passwordRaw;
        this.complexId = complexId;
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordRaw() {
        return passwordRaw;
    }

    public void setPasswordRaw(String passwordRaw) {
        this.passwordRaw = passwordRaw;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }
}
