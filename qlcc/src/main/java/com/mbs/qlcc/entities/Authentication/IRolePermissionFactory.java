package com.mbs.qlcc.entities.Authentication;

public interface IRolePermissionFactory {
    RolePermission createRolePermission(String permissionId, String roleId);
}
