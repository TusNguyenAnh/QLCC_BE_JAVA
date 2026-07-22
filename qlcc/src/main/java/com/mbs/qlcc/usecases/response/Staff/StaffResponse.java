package com.mbs.qlcc.usecases.response.Staff;

public class StaffResponse {
    private String id;
    private String orgId;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String position;

    public StaffResponse(String id, String orgId, String fullname, String email, String phoneNumber, String position) {
        this.id = id;
        this.orgId = orgId;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
