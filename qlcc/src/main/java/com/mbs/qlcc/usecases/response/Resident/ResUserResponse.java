package com.mbs.qlcc.usecases.response.Resident;

import java.time.LocalDateTime;

public class ResUserResponse extends ResidentResponse{
    private String userId;

    public ResUserResponse(String id, String complexId, String fullname, int gender, String email, LocalDateTime birthday, String relationship, String phoneNumber, String cccd, String userId) {
        super(id,complexId,fullname,gender,email,birthday,relationship,phoneNumber,cccd);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
