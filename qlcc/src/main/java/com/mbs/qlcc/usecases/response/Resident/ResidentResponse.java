package com.mbs.qlcc.usecases.response.Resident;

import java.time.LocalDateTime;

public class ResidentResponse {
    private String id;
    private String complexId;
    private String fullname;
    private int gender;
    private String email;
    private LocalDateTime birthday;
    private String relationship;
    private String phoneNumber;
    private String cccd;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResidentResponse() {}

    public ResidentResponse(String id, String complexId, String fullname, int gender, String email, LocalDateTime birthday, String relationship, String phoneNumber, String cccd) {
        this.id = id;
        this.complexId = complexId;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
    }

    public ResidentResponse(String id, String complexId, String fullname, int gender, String email,
                            LocalDateTime birthday, String relationship, String phoneNumber, String cccd,
                            int status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.complexId = complexId;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getComplexId() { return complexId; }
    public void setComplexId(String complexId) { this.complexId = complexId; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getBirthday() { return birthday; }
    public void setBirthday(LocalDateTime birthday) { this.birthday = birthday; }

    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
