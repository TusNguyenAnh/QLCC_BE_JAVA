package com.mbs.qlcc.usecases.request.Organization;

public class CreateOrgUserInpRequest {
    private String userId;
    private String orgId;
    private String roleId;

    public CreateOrgUserInpRequest(String userId, String orgId, String roleId) {
        this.userId = userId;
        this.orgId = orgId;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
