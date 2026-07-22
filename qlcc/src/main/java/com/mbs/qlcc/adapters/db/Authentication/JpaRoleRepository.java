package com.mbs.qlcc.adapters.db.Authentication;

import com.mbs.qlcc.usecases.response.Permission.ICountRoleResponse;
import com.mbs.qlcc.usecases.response.Role.ICountRolePermissionResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface JpaRoleRepository extends JpaRepository<RoleDataMapper, String> {
    Page<IRoleResponse> findByComplexIdAndStatus(String complexId, boolean status, Pageable pageable);

    RoleDataMapper findByRoleNameAndComplexId(String roleName, String complexId);

    @Query("SELECT r.id as roleId, COUNT(ou.userId) as userCount FROM RoleDataMapper r " +
            "JOIN OrgUserDataMapper ou on r.id = ou.roleId " +
            "WHERE r.complexId = :complexId " +
            "GROUP BY r.id")
    List<IRoleUserResponse> countUserById(String complexId);

    @Query("SELECT rp.permissionDataMapper.id as permissionId, COUNT(rp.roleDataMapper.id) as roleCount FROM RoleDataMapper r " +
            "JOIN RolePermissionDataMapper rp on r.id = rp.roleDataMapper.id " +
            "WHERE r.complexId = :complexId " +
            "GROUP BY rp.permissionDataMapper.id")
    List<ICountRoleResponse> countRoleById(String complexId);
}
