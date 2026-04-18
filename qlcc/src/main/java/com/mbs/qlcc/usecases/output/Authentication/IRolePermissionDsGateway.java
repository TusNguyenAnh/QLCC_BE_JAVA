package com.mbs.qlcc.usecases.output.Authentication;

import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Authentication.RolePermission;
import com.mbs.qlcc.entities.Organization.OrgUser;

import java.util.List;

public interface IRolePermissionDsGateway {
    List<Permission> findPermissionByRoleId(String roleId);
}
