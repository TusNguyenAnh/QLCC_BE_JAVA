package com.mbs.qlcc.dto.response.Role;

import com.mbs.qlcc.dto.response.Permission.IPermissionResponse;

import java.util.List;

public interface IRolePermissionResponse {
    List<IPermissionResponse> getPermission();
}
