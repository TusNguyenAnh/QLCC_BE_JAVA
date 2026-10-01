package com.mbs.qlcc.mapper.Authentication;

import com.mbs.qlcc.domain.Permission;
import com.mbs.qlcc.dto.response.Permission.PermissionResponse;

public class PermissionMapper {

    public static PermissionResponse toResponse(Permission permission) {
        if (permission == null) return null;
        return new PermissionResponse(
                permission.getId(),
                permission.getName(),
                permission.getModule(),
                permission.getDescription()
        );
    }

    private PermissionMapper() {}
}
