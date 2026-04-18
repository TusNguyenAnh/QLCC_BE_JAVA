package com.mbs.qlcc.entities.Complex;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Complex Domain Entity
 * Represents a building/apartment complex in the system
 * Status: 0 = Pending, 1 = Approved, 2 = Rejected
 */
public class Complex {
    private String id;
    private String complexName;
    private String address;
    private int totalBuilding;
    private int totalApartment;
    private String nameContact;
    private String phoneContact;
    private String emailContact;
    private String description;
    private String financialModel;
    private int status;  // 0: pending, 1: approved, 2: rejected
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Complex() {
    }

    public Complex(String complexName, String address, int totalBuilding, int totalApartment,
                   String nameContact, String phoneContact, String emailContact, String description,
                   String financialModel) {
        this.id = UUID.randomUUID().toString();
        this.complexName = complexName;
        this.address = address;
        this.totalBuilding = totalBuilding;
        this.totalApartment = totalApartment;
        this.nameContact = nameContact;
        this.phoneContact = phoneContact;
        this.emailContact = emailContact;
        this.description = description;
        this.financialModel = financialModel;
        this.status = 0;  // Default: pending
        this.createdAt = LocalDateTime.now();
    }

    public Complex(String id, String complexName, String address, int totalBuilding, int totalApartment,
                   String nameContact, String phoneContact, String emailContact, String description,
                   String financialModel, int status, LocalDateTime createdAt, LocalDateTime updatedAt,
                   LocalDateTime deletedAt) {
        this.id = id;
        this.complexName = complexName;
        this.address = address;
        this.totalBuilding = totalBuilding;
        this.totalApartment = totalApartment;
        this.nameContact = nameContact;
        this.phoneContact = phoneContact;
        this.emailContact = emailContact;
        this.description = description;
        this.financialModel = financialModel;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

