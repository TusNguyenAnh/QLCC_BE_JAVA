package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Permission.AssignPermissionInpRequest;
import com.mbs.qlcc.usecases.request.Permission.CreatePermissionInpRequest;
import com.mbs.qlcc.usecases.response.Permission.PermissionResponse;

import java.util.List;
import java.util.Map;

public interface IPermissionInputBoundary {
    PermissionResponse createPermission(CreatePermissionInpRequest request);

    Map<String, List<PermissionResponse>> getAllPermissions();

    void assignPermission(AssignPermissionInpRequest assignPermissionInpRequest);
}
