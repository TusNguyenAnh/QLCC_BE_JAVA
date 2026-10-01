package com.mbs.qlcc.mapper.Authentication;

import com.mbs.qlcc.domain.Role;
import com.mbs.qlcc.dto.response.Role.RoleResponse;

public class RoleMapper {

    public static RoleResponse toResponse(Role role) {
        if (role == null) return null;
        return new RoleResponse(
                role.getId(),
                role.getRoleName(),
                role.getComplexId(),
                role.getDescription()
        );
    }

    private RoleMapper() {}
}
