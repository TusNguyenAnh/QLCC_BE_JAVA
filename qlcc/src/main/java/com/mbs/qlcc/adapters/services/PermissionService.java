package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Permission.AssignPermissionRequest;
import com.mbs.qlcc.adapters.request.Permission.CreatePermissionRequest;
import com.mbs.qlcc.usecases.input.IPermissionInputBoundary;
import com.mbs.qlcc.usecases.request.Permission.AssignPermissionInpRequest;
import com.mbs.qlcc.usecases.request.Permission.CreatePermissionInpRequest;
import com.mbs.qlcc.usecases.response.Permission.PermissionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    IPermissionInputBoundary useCase;
    public PermissionResponse create(CreatePermissionRequest request) {
        return useCase.createPermission(new CreatePermissionInpRequest(
                request.getName(),
                request.getModule(),
                request.getDescription()
        ));
    }
    @PreAuthorize("hasAuthority('view:permission')")
    public Map<String, List<PermissionResponse>> getAllPermissions() {
        return useCase.getAllPermissions();
    }

    public String assignPermission(AssignPermissionRequest request) {
        useCase.assignPermission(new AssignPermissionInpRequest(
                request.getRoleId(),
                request.getPermission()
        ));
        return "Gán quyền thành công";
    }
}
