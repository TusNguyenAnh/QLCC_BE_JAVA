package com.mbs.qlcc.entities.Authentication;

public class Role {
    private String id;
    private String roleName;
    private String complexId;
    private String description;

    public Role() {
    }

    public Role(String roleName, String complexId, String description) {
        this.roleName = roleName;
        this.complexId = complexId;
        this.description = description;
    }

    public Role(String id, String roleName, String complexId, String description) {
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
