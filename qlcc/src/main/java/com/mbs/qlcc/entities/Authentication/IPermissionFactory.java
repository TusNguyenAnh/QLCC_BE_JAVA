package com.mbs.qlcc.entities.Authentication;

public interface IPermissionFactory {
    Permission createPermission(String name, String module, String description);
}
