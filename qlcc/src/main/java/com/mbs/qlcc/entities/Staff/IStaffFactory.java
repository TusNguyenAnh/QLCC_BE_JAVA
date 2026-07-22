package com.mbs.qlcc.entities.Staff;

public interface IStaffFactory {
    Staff create(String complexId, String fullname, String email, String phoneNumber, int status);
}
