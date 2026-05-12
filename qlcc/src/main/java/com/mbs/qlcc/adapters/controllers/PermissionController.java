package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Permission.AssignPermissionRequest;
import com.mbs.qlcc.adapters.request.Permission.CreatePermissionRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.PermissionService;
import com.mbs.qlcc.usecases.response.Permission.PermissionResponse;
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
    PermissionService permissionService;

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
