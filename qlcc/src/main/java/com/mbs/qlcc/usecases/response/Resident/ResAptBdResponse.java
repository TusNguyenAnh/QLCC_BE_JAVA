package com.mbs.qlcc.usecases.response.Resident;

import java.time.LocalDateTime;

public class ResAptBdResponse {
    private String id;
    private String complexId;
    private String fullname;
    private int gender;
    private String email;
    private LocalDateTime birthday;
    private String relationship;
    private String phoneNumber;
    private String cccd;
    private String buildingId;
    private int floor;
    private String aptNumber;
    private int status;

    public ResAptBdResponse(String id, String complexId, String fullname, int gender, String email, LocalDateTime birthday, String relationship, String phoneNumber, String cccd, String buildingId, int floor, String aptNumber, int status) {
        this.id = id;
        this.complexId = complexId;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.buildingId = buildingId;
        this.floor = floor;
        this.aptNumber = aptNumber;
        this.status = status;
    }

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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
