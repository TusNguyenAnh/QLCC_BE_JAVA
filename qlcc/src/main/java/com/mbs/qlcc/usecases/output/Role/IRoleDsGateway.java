package com.mbs.qlcc.usecases.output.Role;

import com.mbs.qlcc.entities.Authentication.Role;
import com.mbs.qlcc.usecases.response.PageResponse;

public interface IRoleDsGateway {
    Role createRole(Role role);

    PageResponse<Role> findByComplexId(String complexId, int page, int size);

    Role findByRoleName(String roleName, String complexId);

    Role findById(String roleId);
}
