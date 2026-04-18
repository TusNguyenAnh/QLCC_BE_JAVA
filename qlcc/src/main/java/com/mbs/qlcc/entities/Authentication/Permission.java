package com.mbs.qlcc.entities.Authentication;

public class Permission {
    private String name;
    private String module;
    private String description;

    public Permission() {
    }

    public Permission(String name, String module, String description) {
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
