package com.mbs.qlcc.usecases.output.Authentication;

import com.mbs.qlcc.entities.Authentication.Permission;

import java.util.List;

public interface IPermissionDsGateway {
    Permission store(Permission permission);

    List<Permission> getAllPermission();

    List<String> getPermissionByModule(List<String> module, int type);
}
