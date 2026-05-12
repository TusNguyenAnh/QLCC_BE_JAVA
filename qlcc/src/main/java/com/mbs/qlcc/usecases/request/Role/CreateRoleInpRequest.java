package com.mbs.qlcc.usecases.request.Role;

public class CreateRoleInpRequest {
    private String roleName;
    private String description;
    private String complexId;

    public CreateRoleInpRequest(String roleName, String description, String complexId) {
        this.roleName = roleName;
        this.description = description;
        this.complexId = complexId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }
}
