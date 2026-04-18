package com.mbs.qlcc.adapters.db.Complex;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaComplexRepository extends JpaRepository<ComplexDataMapper, String>,
        JpaSpecificationExecutor<ComplexDataMapper> {

    boolean existsByComplexName(String complexName);

    boolean existsByAddress(String address);

    boolean existsByPhoneContact(String phoneContact);

    Optional<ComplexDataMapper> findByComplexName(String complexName);

    Optional<ComplexDataMapper> findByAddress(String address);

    Optional<ComplexDataMapper> findByPhoneContact(String phoneContact);

    Page<ComplexDataMapper> findByStatus(int status, Pageable pageable);

    @Query("SELECT c FROM ComplexDataMapper c WHERE c.status = :status " +
            "AND (c.complexName LIKE %:keyword% OR c.address LIKE %:keyword% " +
            "OR c.nameContact LIKE %:keyword% OR c.phoneContact LIKE %:keyword%)")
    Page<ComplexDataMapper> findByStatusAndKeyword(@Param("status") int status,
                                                   @Param("keyword") String keyword,
                                                   Pageable pageable);

    @Query("SELECT c FROM ComplexDataMapper c WHERE c.status = :status " +
            "AND c.createdAt BETWEEN :startDate AND :endDate")
    Page<ComplexDataMapper> findByStatusAndCreatedDateRange(@Param("status") int status,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate,
                                                            Pageable pageable);

    @Query("SELECT c FROM ComplexDataMapper c WHERE c.status = :status " +
            "AND (c.complexName LIKE %:keyword% OR c.address LIKE %:keyword% OR c.nameContact LIKE %:keyword% OR c.phoneContact LIKE %:keyword%) " +
            "AND c.createdAt BETWEEN :startDate AND :endDate")
    Page<ComplexDataMapper> findByStatusAndKeywordAndCreatedDateRange(@Param("status") int status,
                                                                      @Param("keyword") String keyword,
                                                                      @Param("startDate") LocalDateTime startDate,
                                                                      @Param("endDate") LocalDateTime endDate,
                                                                      Pageable pageable);

    List<ComplexDataMapper> findAllByStatusAndIdIn(int status, List<String> ids);
}
