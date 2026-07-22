package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Staff.StaffRequest;
import com.mbs.qlcc.usecases.input.IStaffInputBoundary;
import com.mbs.qlcc.usecases.request.Staff.StaffInpRequest;
import com.mbs.qlcc.usecases.response.Staff.StaffResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffService {
    IStaffInputBoundary useCase;
    public StaffResponse create(StaffRequest staffRequest, String complexId) {
        StaffInpRequest staffInpRequest = new StaffInpRequest(
                staffRequest.getFullname(),
                staffRequest.getEmail(),
                staffRequest.getPhoneNumber(),
                staffRequest.getOrgId(),
                staffRequest.getRoleId(),
                complexId
        );
        return useCase.create(staffInpRequest);
    }
}
