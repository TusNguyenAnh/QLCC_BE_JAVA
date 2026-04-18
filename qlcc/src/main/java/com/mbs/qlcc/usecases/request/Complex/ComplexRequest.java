package com.mbs.qlcc.usecases.request.Complex;

public class ComplexRequest {
    private String complex_name;
    private String address;
    private int total_building;
    private int total_apartment;
    private String name_contact;
    private String phone_contact;
    private String email_contact;
    private String description;
    private String financial_model;
    private boolean status;

    public ComplexRequest() {
    }

    public ComplexRequest(String complex_name, String address, int total_building, int total_apartment, String name_contact, String phone_contact, String email_contact, String description, String financial_model, boolean status) {
        this.complex_name = complex_name;
        this.address = address;
        this.total_building = total_building;
        this.total_apartment = total_apartment;
        this.name_contact = name_contact;
        this.phone_contact = phone_contact;
        this.email_contact = email_contact;
        this.description = description;
        this.financial_model = financial_model;
        this.status = status;
    }

    public String getComplex_name() {
        return complex_name;
    }

    public void setComplex_name(String complex_name) {
        this.complex_name = complex_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal_building() {
        return total_building;
    }

    public void setTotal_building(int total_building) {
        this.total_building = total_building;
    }

    public int getTotal_apartment() {
        return total_apartment;
    }

    public void setTotal_apartment(int total_apartment) {
        this.total_apartment = total_apartment;
    }

    public String getName_contact() {
        return name_contact;
    }

    public void setName_contact(String name_contact) {
        this.name_contact = name_contact;
    }

    public String getPhone_contact() {
        return phone_contact;
    }

    public void setPhone_contact(String phone_contact) {
        this.phone_contact = phone_contact;
    }

    public String getEmail_contact() {
        return email_contact;
    }

    public void setEmail_contact(String email_contact) {
        this.email_contact = email_contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinancial_model() {
        return financial_model;
    }

    public void setFinancial_model(String financial_model) {
        this.financial_model = financial_model;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
