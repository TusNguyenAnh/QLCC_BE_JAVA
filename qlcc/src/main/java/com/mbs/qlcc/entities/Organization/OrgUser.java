package com.mbs.qlcc.entities.Organization;

public class OrgUser {
    private String user_id;
    private String org_id;
    private String role_id;

    public OrgUser() {
    }

    public OrgUser(String user_id, String org_id, String role_id) {
        this.user_id = user_id;
        this.org_id = org_id;
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
