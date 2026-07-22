package com.mbs.qlcc.usecases.output.Staff;

import com.mbs.qlcc.entities.Staff.Staff;

import java.util.List;

public interface IStaffDsGateway {
    Staff save(Staff staff);
    List<String> findEmailsByComplexId(String complexId, List<String> emails);
    List<String> findPhoneNumbersByComplexId(String complexId, List<String> phoneNumbers);
}
