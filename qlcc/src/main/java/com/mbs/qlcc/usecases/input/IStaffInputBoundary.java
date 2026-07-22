package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Staff.StaffInpRequest;
import com.mbs.qlcc.usecases.response.Staff.StaffResponse;

public interface IStaffInputBoundary {
    StaffResponse create(StaffInpRequest request);
}
