package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Permission.AssignPermissionRequest;
import com.mbs.qlcc.dto.request.Permission.CreatePermissionRequest;
import com.mbs.qlcc.dto.response.Permission.PermissionResponse;

import java.util.List;
import java.util.Map;

public interface IPermissionService {
    PermissionResponse create(CreatePermissionRequest request);
    Map<String, List<PermissionResponse>> getAllPermissions();
    String assignPermission(AssignPermissionRequest request);
}
