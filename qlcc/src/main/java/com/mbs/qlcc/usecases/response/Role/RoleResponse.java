package com.mbs.qlcc.usecases.response.Role;

public class RoleResponse {
    private String id;
    private String roleName;
    private String complexId;
    private String description;

    public RoleResponse(String id, String roleName, String complexId, String description) {
        this.id = id;
        this.roleName = roleName;
        this.complexId = complexId;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
