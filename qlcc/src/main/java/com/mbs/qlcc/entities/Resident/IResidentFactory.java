package com.mbs.qlcc.entities.Resident;

import java.time.LocalDateTime;

public interface IResidentFactory {
    Resident create(String complexId, String fullname, int gender, String email,
                    LocalDateTime birthday, String relationship, String phoneNumber,
                    String cccd);
}
