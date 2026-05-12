package com.mbs.qlcc.adapters.db.Authentication;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaRolePermissionRepository extends JpaRepository<RolePermissionDataMapper, String> {
    @Query("SELECT p FROM RolePermissionDataMapper rp JOIN rp.permissionDataMapper p WHERE rp.roleDataMapper.id = :roleId")
    List<PermissionDataMapper> findByRoleIdFetchPermission(String roleId);
    @Modifying
    @Transactional
    @Query("DELETE FROM RolePermissionDataMapper rp WHERE rp.roleDataMapper.id = :roleId")
    void deleteWithRoleId(String roleId);
}
