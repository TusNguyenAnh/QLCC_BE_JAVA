package com.mbs.qlcc.usecases.request.Permission;

import java.util.List;

public class AssignPermissionInpRequest {
    private String roleId;
    private List<String> permission;

    public AssignPermissionInpRequest(String roleId, List<String> permission) {
        this.roleId = roleId;
        this.permission = permission;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }
}
