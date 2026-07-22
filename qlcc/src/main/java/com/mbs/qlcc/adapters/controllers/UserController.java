package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.User.UserFilterRequest;
import com.mbs.qlcc.adapters.request.User.UserRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.UserService;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService service;

    @PostMapping("/filter")
    public ApiResponse<List<IResUserResponse>> filterUser(@RequestBody UserFilterRequest request) {
        String complexId = getCurrentComplexId();
        return ApiResponse.<List<IResUserResponse>>builder()
                .result(service.filterUser(request, complexId))
                .build();
    }

    @GetMapping("/staff/{orgId}")
    public ApiResponse<List<IStaffUserResponse>> findStaffByOrgId(@PathVariable String orgId) {
        return ApiResponse.<List<IStaffUserResponse>>builder()
                .result(service.findStaffByOrgId(orgId))
                .build();
    }

    @GetMapping("/res/{orgId}")
    public ApiResponse<List<IResUserResponse>> findResByOrgId(@PathVariable String orgId) {
        return ApiResponse.<List<IResUserResponse>>builder()
                .result(service.findResByOrgId(orgId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<String> create(@RequestBody List<UserRequest> req) {
        String complexId = getCurrentComplexId();
        service.create(req, complexId);
        return ApiResponse.<String>builder()
                .result("Create user successfully")
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }
}
