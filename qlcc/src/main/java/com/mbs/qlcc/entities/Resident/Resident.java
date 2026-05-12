package com.mbs.qlcc.entities.Resident;

import java.time.LocalDateTime;
import java.util.UUID;

public class Resident {
    private String id;
    private String complexId;
    private String fullname;
    private int gender; // 0: Male, 1: Female
    private String email;
    private LocalDateTime birthday;
    private String relationship; // e.g., "Chủ hộ", "Thành viên gia đình"
    private String phoneNumber;
    private String cccd; // Căn cước công dân
    private int status; // 0: Active, 1: Inactive
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt; // Soft delete


    public Resident() {
        this.status = 0; // Active by default
        this.createdAt = LocalDateTime.now();
    }


    public Resident(String id, String complexId, String fullname, int gender, String email,
                    LocalDateTime birthday, String relationship, String phoneNumber, String cccd,
                    int status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.complexId = complexId;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.status = status;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
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

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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
