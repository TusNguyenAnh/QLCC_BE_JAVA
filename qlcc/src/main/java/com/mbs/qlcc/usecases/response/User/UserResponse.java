package com.mbs.qlcc.usecases.response.User;

public class UserResponse {
    private String username;
    private String resId;
    private String complexId;
    private boolean status;

    public UserResponse(String username, String resId, String complexId, boolean status) {
        this.username = username;
        this.resId = resId;
        this.complexId = complexId;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
