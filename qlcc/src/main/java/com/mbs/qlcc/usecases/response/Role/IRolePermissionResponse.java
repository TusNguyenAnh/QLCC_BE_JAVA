package com.mbs.qlcc.usecases.response.Role;

import com.mbs.qlcc.usecases.response.Permission.IPermissionResponse;

import java.util.List;

public interface IRolePermissionResponse {
    List<IPermissionResponse> getPermissionDataMapper();
}
