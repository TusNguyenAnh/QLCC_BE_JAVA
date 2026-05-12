package com.mbs.qlcc.usecases.response.Organization;

public class OrgUserResponse {
    private String id;
    private String userId;
    private String orgId;
    private String roleId;

    public OrgUserResponse() {
    }

    public OrgUserResponse(String id, String userId, String orgId, String roleId) {
        this.id = id;
        this.userId = userId;
        this.orgId = orgId;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
