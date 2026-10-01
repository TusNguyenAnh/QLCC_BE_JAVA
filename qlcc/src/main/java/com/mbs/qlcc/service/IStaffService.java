package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Staff.StaffRequest;
import com.mbs.qlcc.dto.response.Staff.StaffResponse;

public interface IStaffService {
    StaffResponse create(StaffRequest request, String complexId);
}
