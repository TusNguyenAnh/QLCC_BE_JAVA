package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Role.AssignRoleRequest;
import com.mbs.qlcc.adapters.request.Role.CreateRoleRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.RoleService;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleResponse;
import com.mbs.qlcc.usecases.response.Role.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping
    public ApiResponse<PageResponse<IRoleResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int perPage) {

        return ApiResponse.<PageResponse<IRoleResponse>>builder()
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
    public ApiResponse<String> getRoleByUserId(@PathVariable String userId, @PathVariable String orgId) {
        return ApiResponse.<String>builder()
                .result(roleService.getRoleByUserId(userId, orgId))
                .build();
    }

    @GetMapping("/count-user")
    public ApiResponse<Map<String, Integer>> countUserByRole() {
        return ApiResponse.<Map<String, Integer>>builder()
                .result(roleService.getRoleUserCount())
                .build();
    }

    @GetMapping("/count-permission")
    public ApiResponse<Map<String, Integer>> countPermissionByRole() {
        return ApiResponse.<Map<String, Integer>>builder()
                .result(roleService.getRolePermissionCount())
                .build();
    }

}
