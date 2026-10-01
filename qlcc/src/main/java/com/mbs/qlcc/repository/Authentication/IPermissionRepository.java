package com.mbs.qlcc.repository.Authentication;

import com.mbs.qlcc.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String> {

    @Query("SELECT p.id FROM Permission p WHERE " +
            "(:type = 1 AND p.module NOT IN :module)" +
            "OR" +
            "(:type <> 1 AND p.module IN :module)")
    List<String> findIdsByModule(@Param("module") List<String> module, @Param("type") int type);
}
