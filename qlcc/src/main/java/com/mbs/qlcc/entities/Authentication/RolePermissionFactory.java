package com.mbs.qlcc.entities.Authentication;

public class RolePermissionFactory implements IRolePermissionFactory{
    @Override
    public RolePermission createRolePermission(String permissionId, String roleId) {
        return new RolePermission(permissionId, roleId);
    }
}
