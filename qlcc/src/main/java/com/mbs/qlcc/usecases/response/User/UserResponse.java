package com.mbs.qlcc.usecases.response.User;

public class UserResponse {
    private String username;
    private String res_id;
    private boolean status;

    public UserResponse() {
    }

    public UserResponse(String username, String res_id, boolean status) {
        this.username = username;
        this.res_id = res_id;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
