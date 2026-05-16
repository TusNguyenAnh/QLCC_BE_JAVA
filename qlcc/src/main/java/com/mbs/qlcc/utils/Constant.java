package com.mbs.qlcc.utils;

public enum Constant {
    SUBJECT("Thông tin đăng ký tài khoản"),
    SYSTEM_NAME("Hệ thống quản lý chung cư"),
    ROLE_ADMIN("admin"),
    CENTRALIZED_FINANCIAL_MODEL("centralized"),
    DECENTRALIZED_FINANCIAL_MODEL("decentralized")
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
