package com.mbs.qlcc.adapters.db.Authentication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<RoleDataMapper, String> {
    Page<RoleDataMapper> findByComplexIdAndStatus(String complexId, boolean status, Pageable pageable);
    RoleDataMapper findByRoleNameAndComplexId(String roleName, String complexId);
}
