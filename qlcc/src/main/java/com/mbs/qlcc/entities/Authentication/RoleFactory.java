package com.mbs.qlcc.entities.Authentication;

public class RoleFactory implements IRoleFactory {
    @Override
    public Role createRole(String roleName, String complexId, String description) {
        return new Role(roleName, complexId, description);
    }
}
