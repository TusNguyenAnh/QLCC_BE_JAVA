package com.mbs.qlcc.usecases.response.Authentication;

import com.mbs.qlcc.usecases.response.User.UserResponse;

import java.util.List;

public class ProfileResponse {
    String orgId;
    UserResponse user;
    String permissions;

    public ProfileResponse() {
    }

    public ProfileResponse(String orgId, UserResponse user, String permissions) {
        this.orgId = orgId;
        this.user = user;
        this.permissions = permissions;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
