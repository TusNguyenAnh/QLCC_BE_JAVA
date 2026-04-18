package com.mbs.qlcc.adapters.db.Authentication;

import com.mbs.qlcc.adapters.db.Organization.JpaOrgUserRepository;
import com.mbs.qlcc.adapters.db.Organization.OrgUserDataMapper;
import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Authentication.RolePermission;
import com.mbs.qlcc.entities.Organization.OrgUser;
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
}
