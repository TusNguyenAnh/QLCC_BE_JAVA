package com.mbs.qlcc.entities.Resident;

import java.time.LocalDateTime;

public class ResidentFactory implements IResidentFactory {

    @Override
    public Resident create(String complexId, String fullname, int gender, String email,
                          LocalDateTime birthday, String relationship, String phoneNumber,
                          String cccd) {
        Resident resident = new Resident();
        resident.setComplexId(complexId);
        resident.setFullname(fullname);
        resident.setGender(gender);
        resident.setEmail(email);
        resident.setBirthday(birthday);
        resident.setRelationship(relationship);
        resident.setPhoneNumber(phoneNumber);
        resident.setCccd(cccd);
        resident.setStatus(0); // Active by default
        resident.setCreatedAt(LocalDateTime.now());

        return resident;
    }
}
