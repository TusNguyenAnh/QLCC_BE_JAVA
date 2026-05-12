package com.mbs.qlcc.adapters.db.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaPermissionRepository extends JpaRepository<PermissionDataMapper, String> {
    @Query("SELECT p.id FROM PermissionDataMapper p WHERE " +
            "(:type = 1 AND p.module NOT IN :module)" +
            "OR" +
            "(:type <> 1 AND p.module IN :module)")
    List<String> findByModule(List<String> module, int type);


}
