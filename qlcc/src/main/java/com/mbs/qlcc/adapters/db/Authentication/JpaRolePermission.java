package com.mbs.qlcc.adapters.db.Authentication;

import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Authentication.RolePermission;
import com.mbs.qlcc.usecases.output.Authentication.IRolePermissionDsGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JpaRolePermission implements IRolePermissionDsGateway {
    final JpaRolePermissionRepository repository;

    @Override
    public List<Permission> findPermissionByRoleId(String roleId) {
        var listPermissionDataMappers = repository.findByRoleIdFetchPermission(roleId);
        return listPermissionDataMappers.stream()
                .map(p -> new Permission(p.getName(), p.getModule(), p.getDescription()))
                .toList();
    }

    @Override
    public void saveAll(List<RolePermission> rolePermissions) {
        var listRolePermissionDataMappers = rolePermissions.stream()
                .map(this::mapToDataMapper)
                .toList();
        repository.saveAll(listRolePermissionDataMappers);
    }

    @Override
    public void deleteByRoleId(String roleId) {
        repository.deleteWithRoleId(roleId);
    }

    private RolePermissionDataMapper mapToDataMapper(RolePermission rolePermission) {
        RolePermissionDataMapper dataMapper = new RolePermissionDataMapper();
        dataMapper.setPermissionDataMapper(PermissionDataMapper.builder().id(rolePermission.getPermissionId()).build());
        dataMapper.setRoleDataMapper(RoleDataMapper.builder().id(rolePermission.getRoleId()).build());
        return dataMapper;
    }


}
