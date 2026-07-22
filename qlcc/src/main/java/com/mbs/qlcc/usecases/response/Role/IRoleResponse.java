package com.mbs.qlcc.usecases.response.Role;

import java.util.List;

public interface IRoleResponse {
    String getId();

    String getRoleName();

    String getComplexId();

    String getDescription();
    Integer totalPermission();
    Integer totalUser();
    List<IRolePermissionResponse> getRolePermission();
}
