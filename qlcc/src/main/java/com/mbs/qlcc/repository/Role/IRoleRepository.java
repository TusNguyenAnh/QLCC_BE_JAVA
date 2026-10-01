package com.mbs.qlcc.repository.Role;

import com.mbs.qlcc.domain.Role;
import com.mbs.qlcc.dto.response.Permission.ICountRoleResponse;
import com.mbs.qlcc.dto.response.Role.IRoleResponse;
import com.mbs.qlcc.dto.response.Role.IRoleUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {

    Page<IRoleResponse> findByComplexIdAndStatus(String complexId, boolean status, Pageable pageable);

    Role findByRoleNameAndComplexId(String roleName, String complexId);

    @Query("SELECT r.id as roleId, COUNT(ou.userId) as userCount FROM Role r " +
            "JOIN OrgUser ou on r.id = ou.roleId " +
            "WHERE r.complexId = :complexId " +
            "GROUP BY r.id")
    List<IRoleUserResponse> countUserById(@Param("complexId") String complexId);

    @Query("SELECT rp.permission.id as permissionId, COUNT(rp.role.id) as roleCount FROM Role r " +
            "JOIN RolePermission rp on r.id = rp.role.id " +
            "WHERE r.complexId = :complexId " +
            "GROUP BY rp.permission.id")
    List<ICountRoleResponse> countRoleById(@Param("complexId") String complexId);
}
