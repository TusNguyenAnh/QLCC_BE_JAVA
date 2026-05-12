package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Role.AssignRoleRequest;
import com.mbs.qlcc.adapters.request.Role.CreateRoleRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.RoleService;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Role.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping
    public ApiResponse<PageResponse<RoleResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int perPage) {

        return ApiResponse.<PageResponse<RoleResponse>>builder()
                .result(roleService.getAllRoles(page, Math.min(perPage, 50)))
                .build();
    }

    @PostMapping
    public ApiResponse<RoleResponse> create(
            @RequestBody CreateRoleRequest request) {

        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @PostMapping("/user")
    public ApiResponse<String> assignRole(@RequestBody AssignRoleRequest request) {
        return ApiResponse.<String>builder()
                .result(roleService.assignRole(request))
                .build();
    }

    @GetMapping("/user/{userId}/org/{orgId}")
    public ApiResponse<String> getRoleByUserId(String userId, String orgId) {
        return ApiResponse.<String>builder()
                .result(roleService.getRoleByUserId(userId, orgId))
                .build();
    }
}
