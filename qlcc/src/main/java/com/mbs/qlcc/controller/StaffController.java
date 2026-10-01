package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Staff.StaffRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.Staff.StaffResponse;
import com.mbs.qlcc.service.IStaffService;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {

    IStaffService staffService;

    @PostMapping("")
    public ApiResponse<StaffResponse> create(@RequestBody StaffRequest req) {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        return ApiResponse.<StaffResponse>builder()
                .result(staffService.create(req, complexId))
                .build();
    }
}
