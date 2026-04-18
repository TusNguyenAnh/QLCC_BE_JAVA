package com.mbs.qlcc.utils;

public enum Constant {
    SUBJECT("Thông tin đăng ký tài khoản"),
    SYSTEM_NAME("Hệ thống quản lý chung cư"),
    ;
    private String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
