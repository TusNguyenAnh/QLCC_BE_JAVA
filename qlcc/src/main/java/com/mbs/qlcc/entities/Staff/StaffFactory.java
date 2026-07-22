package com.mbs.qlcc.entities.Staff;

import java.time.LocalDateTime;

public class StaffFactory implements IStaffFactory {
    @Override
    public Staff create(String complexId, String fullname, String email, String phoneNumber, int status) {
        return new Staff(complexId, fullname, email, phoneNumber, status, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
