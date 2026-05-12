package com.mbs.qlcc.usecases.request.Resident;

import java.time.LocalDateTime;

public class CreateResidentInpRequest {
    private String complexId;
    private String fullname;
    private int gender;
    private String email;
    private LocalDateTime birthday;
    private String relationship;
    private String phoneNumber;
    private String cccd;

    public CreateResidentInpRequest() {
    }

    public CreateResidentInpRequest(String complexId, String fullname, int gender, String email,
                                    LocalDateTime birthday, String relationship, String phoneNumber,
                                    String cccd) {
        this.complexId = complexId;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
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
}
