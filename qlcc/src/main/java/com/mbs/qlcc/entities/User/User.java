package com.mbs.qlcc.entities.User;

public class User {
    private String id;
    private String username;
    private String password;
    private String res_id;
    private String staff_id;
    private boolean status;
    private String complex_id;

    public User() {
    }

    public User(String username, String password, String res_id, String staff_id, boolean status, String complex_id) {
        this.username = username;
        this.password = password;
        this.res_id = res_id;
        this.staff_id = staff_id;
        this.status = status;
        this.complex_id = complex_id;
    }

    public User(String id, String username, String password, String res_id, String staff_id, boolean status, String complex_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.res_id = res_id;
        this.staff_id = staff_id;
        this.status = status;
        this.complex_id = complex_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getComplex_id() {
        return complex_id;
    }

    public void setComplex_id(String complex_id) {
        this.complex_id = complex_id;
    }
}
