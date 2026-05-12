package com.mbs.qlcc.entities.Authentication;

public class PermissionFactory implements IPermissionFactory{
    @Override
    public Permission createPermission(String name, String module, String description) {
        return new Permission(name, module, description);
    }
}
