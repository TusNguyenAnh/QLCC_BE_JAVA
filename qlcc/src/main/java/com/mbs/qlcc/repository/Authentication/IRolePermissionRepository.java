package com.mbs.qlcc.repository.Authentication;

import com.mbs.qlcc.domain.Permission;
import com.mbs.qlcc.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IRolePermissionRepository extends JpaRepository<RolePermission, String> {

    @Query("SELECT rp.permission FROM RolePermission rp WHERE rp.role.id = :roleId")
    List<Permission> findPermissionsByRoleId(@Param("roleId") String roleId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RolePermission rp WHERE rp.role.id = :roleId")
    void deleteByRoleId(@Param("roleId") String roleId);
}
