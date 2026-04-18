package com.mbs.qlcc.usecases.request.User;

public class UserInpRequest {
    private String phoneNumber;
    private String password;
    private String cccd;
    private String fullname;
    private String email;
    private String complexId;
    private String resId;
    private String staffId;

    public UserInpRequest() {
    }

    public UserInpRequest(String phoneNumber, String password, String complexId, String resId, String staffId) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.complexId = complexId;
        this.resId = resId;
        this.staffId = staffId;
    }

    public UserInpRequest(String phoneNumber, String password, String cccd, String fullname, String email, String complexId, String resId, String staffId) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.cccd = cccd;
        this.fullname = fullname;
        this.email = email;
        this.complexId = complexId;
        this.resId = resId;
        this.staffId = staffId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComplex_id() {
        return complexId;
    }

    public void setComplex_id(String complex_id) {
        this.complexId = complexId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
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
}
