package com.mbs.qlcc.entities.Resident;

import java.time.LocalDateTime;

public class AptResidentFactory implements IAptResidentFactory {

    @Override
    public AptResident create(String aptId, String residentId) {
        AptResident aptResident = new AptResident();
        aptResident.setAptId(aptId);
        aptResident.setResidentId(residentId);
        aptResident.setStatus(0); // Active by default
        aptResident.setCreatedAt(LocalDateTime.now());

        return aptResident;
    }
}
