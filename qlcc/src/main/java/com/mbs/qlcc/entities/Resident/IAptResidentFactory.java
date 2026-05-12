package com.mbs.qlcc.entities.Resident;

public interface IAptResidentFactory {
    AptResident create(String aptId, String residentId);
}
