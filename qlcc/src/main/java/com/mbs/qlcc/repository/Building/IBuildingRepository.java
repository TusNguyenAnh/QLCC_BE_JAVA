package com.mbs.qlcc.repository.Building;

import com.mbs.qlcc.domain.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBuildingRepository extends JpaRepository<Building, String>, JpaSpecificationExecutor<Building> {

    @Query("SELECT b FROM Building b WHERE b.complexId = :complexId AND b.deletedAt IS NULL ORDER BY b.createdAt DESC")
    List<Building> findByComplexId(@Param("complexId") String complexId);

    Page<Building> findByComplexIdAndDeletedAtIsNull(String complexId, Pageable pageable);

    Optional<Building> findByIdAndDeletedAtIsNull(String id);

    boolean existsByBuildingNameAndComplexIdAndDeletedAtIsNull(String buildingName, String complexId);

    List<Building> findAllByIdInAndComplexIdAndDeletedAtIsNull(Collection<String> ids, String complexId);

    List<Building> findAllByBuildingNameInAndComplexIdAndDeletedAtIsNull(Collection<String> buildingNames, String complexId);
}
