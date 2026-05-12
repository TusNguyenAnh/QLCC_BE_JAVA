package com.mbs.qlcc.usecases.request.Role;

public class AssignRoleInpRequest {
    private String userId;
    private String roleId;
    private String orgId;

    public AssignRoleInpRequest(String userId, String roleId, String orgId) {
        this.userId = userId;
        this.roleId = roleId;
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
