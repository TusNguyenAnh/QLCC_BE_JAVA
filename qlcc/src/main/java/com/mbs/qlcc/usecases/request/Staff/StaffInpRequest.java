package com.mbs.qlcc.usecases.request.Staff;

public class StaffInpRequest {
    private String fullname;
    private String email;
    private String phoneNumber;
    private String orgId;
    private String roleId;

    private String complexId;

    public StaffInpRequest(String fullname, String email, String phoneNumber, String orgId, String roleId, String complexId) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orgId = orgId;
        this.roleId = roleId;
        this.complexId = complexId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
