package com.mbs.qlcc.adapters.db.Building;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface JpaBuildingRepository extends JpaRepository<BuildingDataMapper, String>,
        JpaSpecificationExecutor<BuildingDataMapper> {

    // Existence checks
    boolean existsByBuildingNameAndComplexId(String buildingName, String complexId);

    // Find by ID (excluding soft-deleted)
    @Query("SELECT b FROM BuildingDataMapper b WHERE b.id = :id AND b.deletedAt IS NULL")
    Optional<BuildingDataMapper> findById(@Param("id") String id);

    // Find by complex ID (excluding soft-deleted)
    @Query("SELECT b FROM BuildingDataMapper b WHERE b.complexId = :complexId AND b.deletedAt IS NULL ORDER BY b.createdAt DESC")
    List<BuildingDataMapper> findByComplexId(@Param("complexId") String complexId);

    // Find by complex ID with pagination (excluding soft-deleted)
    @Query("SELECT b FROM BuildingDataMapper b WHERE b.complexId = :complexId AND b.deletedAt IS NULL ORDER BY b.createdAt DESC")
    Page<BuildingDataMapper> findByComplexId(@Param("complexId") String complexId, Pageable pageable);

    // Find by IDs and complex ID (excluding soft-deleted) - for validation
    @Query("SELECT b FROM BuildingDataMapper b WHERE b.id IN :ids AND b.complexId = :complexId AND b.deletedAt IS NULL")
    List<BuildingDataMapper> findByIdInAndComplexId(@Param("ids") List<String> ids, @Param("complexId") String complexId);

    List<BuildingDataMapper> findByBuildingNameInAndComplexId(Set<String> name, String complexId);


    // Get financial ratios for buildings
    @Query("SELECT b FROM BuildingDataMapper b WHERE b.id IN :ids AND b.complexId = :complexId AND b.financialRatio IS NOT NULL AND b.deletedAt IS NULL")
    List<BuildingDataMapper> findWithRatioByIdInAndComplexId(@Param("ids") List<String> ids, @Param("complexId") String complexId);

    // Update financial ratio
    @Modifying
    @Query("UPDATE BuildingDataMapper b SET b.financialRatio = :ratio WHERE b.id = :id")
    void updateFinancialRatio(@Param("id") String id, @Param("ratio") Float ratio);

    // Soft delete - update status
    @Modifying
    @Query("UPDATE BuildingDataMapper b SET b.status = 1, b.deletedAt = CURRENT_TIMESTAMP WHERE b.id IN :ids")
    void softDeleteByIds(@Param("ids") List<String> ids);
}
