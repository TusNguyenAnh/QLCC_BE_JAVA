package com.mbs.qlcc.entities.Authentication;

public class RolePermission {
    private String id;
    private String permissionId;
    private String roleId;

    public RolePermission() {
    }

    public RolePermission(String permissionId, String roleId) {
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public RolePermission(String id, String permissionId, String roleId) {
        this.id = id;
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
