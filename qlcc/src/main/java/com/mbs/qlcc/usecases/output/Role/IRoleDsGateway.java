package com.mbs.qlcc.usecases.output.Role;

import com.mbs.qlcc.entities.Authentication.Role;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleResponse;

import java.util.Map;

public interface IRoleDsGateway {
    Role createRole(Role role);

    PageResponse<IRoleResponse> findByComplexId(String complexId, int page, int size);

    Role findByRoleName(String roleName, String complexId);

    Role findById(String roleId);
    Map<String, Integer> countUserById(String complexId);
    Map<String, Integer> countRoleById(String complexId);

}
