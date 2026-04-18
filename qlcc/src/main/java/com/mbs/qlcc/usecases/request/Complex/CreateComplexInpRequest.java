package com.mbs.qlcc.usecases.request.Complex;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


public class CreateComplexInpRequest {
    String complexName;
    String address;
    int totalBuilding;
    int totalApartment;
    String nameContact;
    String phoneContact;
    String emailContact;
    String description;
    String financialModel;

    public CreateComplexInpRequest() {
    }

    public CreateComplexInpRequest(String complexName, String address, int totalBuilding, int totalApartment, String nameContact, String phoneContact, String emailContact, String description, String financialModel) {
        this.complexName = complexName;
        this.address = address;
        this.totalBuilding = totalBuilding;
        this.totalApartment = totalApartment;
        this.nameContact = nameContact;
        this.phoneContact = phoneContact;
        this.emailContact = emailContact;
        this.description = description;
        this.financialModel = financialModel;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalBuilding() {
        return totalBuilding;
    }

    public void setTotalBuilding(int totalBuilding) {
        this.totalBuilding = totalBuilding;
    }

    public int getTotalApartment() {
        return totalApartment;
    }

    public void setTotalApartment(int totalApartment) {
        this.totalApartment = totalApartment;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinancialModel() {
        return financialModel;
    }

    public void setFinancialModel(String financialModel) {
        this.financialModel = financialModel;
    }
}
