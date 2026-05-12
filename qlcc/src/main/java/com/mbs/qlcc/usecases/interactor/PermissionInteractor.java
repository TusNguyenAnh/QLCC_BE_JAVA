package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.adapters.request.Permission.AssignPermissionRequest;
import com.mbs.qlcc.entities.Authentication.IPermissionFactory;
import com.mbs.qlcc.entities.Authentication.IRolePermissionFactory;
import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Authentication.RolePermission;
import com.mbs.qlcc.usecases.input.IPermissionInputBoundary;
import com.mbs.qlcc.usecases.output.Authentication.IPermissionDsGateway;
import com.mbs.qlcc.usecases.output.Authentication.IRolePermissionDsGateway;
import com.mbs.qlcc.usecases.request.Permission.AssignPermissionInpRequest;
import com.mbs.qlcc.usecases.request.Permission.CreatePermissionInpRequest;
import com.mbs.qlcc.usecases.response.Permission.PermissionResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PermissionInteractor implements IPermissionInputBoundary {
    private final IPermissionFactory permissionFactory;
    private final IPermissionDsGateway permissionDsGateway;
    private final IRolePermissionFactory rolePermissionFactory;
    private final IRolePermissionDsGateway rolePermissionDsGateway;

    public PermissionInteractor(IPermissionFactory permissionFactory, IPermissionDsGateway permissionDsGateway, IRolePermissionFactory rolePermissionFactory, IRolePermissionDsGateway rolePermissionDsGateway) {
        this.permissionFactory = permissionFactory;
        this.permissionDsGateway = permissionDsGateway;
        this.rolePermissionFactory = rolePermissionFactory;
        this.rolePermissionDsGateway = rolePermissionDsGateway;
    }

    @Override
    public PermissionResponse createPermission(CreatePermissionInpRequest request) {
        Permission permission = permissionFactory.createPermission(request.getName(), request.getModule(), request.getDescription());
        return mapToPermissionResponse(permissionDsGateway.store(permission));
    }

    @Override
    public Map<String, List<PermissionResponse>> getAllPermissions() {
        List<Permission> permissions = permissionDsGateway.getAllPermission();

        return permissions.stream()
                .map(this::mapToPermissionResponse)
                .collect(Collectors.groupingBy(PermissionResponse::getModule));
    }

    @Override
    public void assignPermission(AssignPermissionInpRequest assignPermissionInpRequest) {
        rolePermissionDsGateway.deleteByRoleId(assignPermissionInpRequest.getRoleId());
        String roleId = assignPermissionInpRequest.getRoleId();
        List<RolePermission> rolePermissions = assignPermissionInpRequest.getPermission().stream()
                .map(permissionId -> rolePermissionFactory.createRolePermission(permissionId, roleId))
                .toList();
        rolePermissionDsGateway.saveAll(rolePermissions);
    }


    private PermissionResponse mapToPermissionResponse(Permission permission) {
        return new PermissionResponse(permission.getId(), permission.getName(), permission.getModule(), permission.getDescription());
    }

}
