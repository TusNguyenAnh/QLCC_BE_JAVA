package com.mbs.qlcc.usecases.request.Permission;

public class CreatePermissionInpRequest {
    private String name;
    private String module;
    private String description;

    public CreatePermissionInpRequest(String name, String module, String description) {
        this.name = name;
        this.module = module;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
