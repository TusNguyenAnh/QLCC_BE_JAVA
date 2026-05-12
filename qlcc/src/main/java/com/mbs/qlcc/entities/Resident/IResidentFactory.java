package com.mbs.qlcc.entities.Resident;

public interface IResidentFactory {
    Resident create(String complexId, String fullname, int gender, String email,
                    java.time.LocalDateTime birthday, String relationship, String phoneNumber,
                    String cccd);
}
