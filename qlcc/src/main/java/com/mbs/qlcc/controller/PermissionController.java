package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Permission.AssignPermissionRequest;
import com.mbs.qlcc.dto.request.Permission.CreatePermissionRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.Permission.PermissionResponse;
import com.mbs.qlcc.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    IPermissionService permissionService;

    @GetMapping
    public ApiResponse<Map<String, List<PermissionResponse>>> index() {
        return ApiResponse.<Map<String, List<PermissionResponse>>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @PostMapping
    public ApiResponse<PermissionResponse> create(
            @RequestBody CreatePermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @PostMapping("/assign")
    public ApiResponse<String> assignPermission(@RequestBody AssignPermissionRequest request) {
        return ApiResponse.<String>builder()
                .result(permissionService.assignPermission(request))
                .build();
    }
}
