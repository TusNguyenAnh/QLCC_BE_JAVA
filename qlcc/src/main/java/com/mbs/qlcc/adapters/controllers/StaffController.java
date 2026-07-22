package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Staff.StaffRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.StaffService;
import com.mbs.qlcc.usecases.response.Staff.StaffResponse;
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
    StaffService service;

    @PostMapping("")
    public ApiResponse<StaffResponse> create(@RequestBody StaffRequest req) {
        String complexId = getCurrentComplexId();
        return ApiResponse.<StaffResponse>builder()
                .result(service.create(req, complexId))
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }

}
