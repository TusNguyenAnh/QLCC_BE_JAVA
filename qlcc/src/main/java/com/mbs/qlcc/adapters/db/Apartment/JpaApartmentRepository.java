package com.mbs.qlcc.adapters.db.Apartment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface JpaApartmentRepository extends JpaRepository<ApartmentDataMapper, String>,
        JpaSpecificationExecutor<ApartmentDataMapper> {

    // Existence checks
    boolean existsByBuildingDataMapper_IdAndAptNumber(String buildingId, String aptNumber);

    List<ApartmentDataMapper> findByBuildingDataMapper_IdAndAptNumberIn(String buildingId, List<String> aptNumbers);

    List<ApartmentDataMapper> findByBuildingDataMapper_IdIn(List<String> buildingId);

    // Find by specific field
    Optional<ApartmentDataMapper> findByIdAndDeletedAtIsNull(String id);

    Page<ApartmentDataMapper> findByBuildingDataMapper_IdAndDeletedAtIsNull(String buildingId, Pageable pageable);

    // Filter by status
    Page<ApartmentDataMapper> findByStatusAndDeletedAtIsNull(int status, Pageable pageable);

    // Find all by complex
    Page<ApartmentDataMapper> findByComplexIdAndDeletedAtIsNull(String complexId, Pageable pageable);

    // Find all by building and complex
    List<ApartmentDataMapper> findByBuildingDataMapper_IdAndComplexIdAndDeletedAtIsNull(String buildingId, String complexId);

    // Advanced keyword search
    @Query("SELECT a FROM ApartmentDataMapper a WHERE a.status = :status " +
            "AND a.deletedAt IS NULL " +
            "AND (CAST(a.floor AS string) LIKE %:keyword% " +
            "OR a.aptNumber LIKE %:keyword% " +
            "OR a.aptType LIKE %:keyword%)")
    Page<ApartmentDataMapper> findByStatusAndKeyword(int status, String keyword, Pageable pageable);

    // Date range filter
    @Query("SELECT a FROM ApartmentDataMapper a WHERE a.status = :status " +
            "AND a.deletedAt IS NULL " +
            "AND a.createdAt BETWEEN :startDate AND :endDate")
    Page<ApartmentDataMapper> findByStatusAndCreatedDateRange(int status, LocalDateTime startDate,
                                                              LocalDateTime endDate, Pageable pageable);

    // Keyword + Date range
    @Query("SELECT a FROM ApartmentDataMapper a WHERE a.status = :status " +
            "AND a.deletedAt IS NULL " +
            "AND (CAST(a.floor AS string) LIKE %:keyword% " +
            "OR a.aptNumber LIKE %:keyword% " +
            "OR a.aptType LIKE %:keyword%) " +
            "AND a.createdAt BETWEEN :startDate AND :endDate")
    Page<ApartmentDataMapper> findByStatusAndKeywordAndDateRange(int status, String keyword,
                                                                 LocalDateTime startDate,
                                                                 LocalDateTime endDate, Pageable pageable);

    // For approval/rejection batch operations
    List<ApartmentDataMapper> findAllByStatusAndIdInAndDeletedAtIsNull(int status, List<String> ids);
}
