package com.mbs.qlcc.adapters.db.Authentication;

import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.usecases.output.Authentication.IPermissionDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaPermission implements IPermissionDsGateway {
    private final JpaPermissionRepository permissionRepository;

    @Override
    public Permission store(Permission permission) {
        PermissionDataMapper permissionDataMapper = PermissionDataMapper.builder()
                .name(permission.getName())
                .module(permission.getModule())
                .description(permission.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return mapToPermission(permissionRepository.save(permissionDataMapper));
    }

    @Override
    public List<Permission> getAllPermission() {
        List<PermissionDataMapper> listPermissionDataMappers = permissionRepository.findAll();
        return listPermissionDataMappers.stream()
                .map(this::mapToPermission)
                .toList();
    }

    @Override
    public List<String> getPermissionByModule(List<String> module, int type) {
        return permissionRepository.findByModule(module, type);
    }

    private Permission mapToPermission(PermissionDataMapper permissionDataMapper) {
        return new Permission(permissionDataMapper.getName(), permissionDataMapper.getModule(), permissionDataMapper.getDescription());
    }

}
