package com.mbs.qlcc.repository.Complex;

import com.mbs.qlcc.domain.Complex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IComplexRepository extends JpaRepository<Complex, String>,
        JpaSpecificationExecutor<Complex> {

    boolean existsByComplexName(String complexName);

    boolean existsByAddress(String address);

    boolean existsByPhoneContact(String phoneContact);

    Optional<Complex> findByComplexName(String complexName);

    Optional<Complex> findByAddress(String address);

    Optional<Complex> findByPhoneContact(String phoneContact);

    Page<Complex> findByStatus(int status, Pageable pageable);

    @Query("SELECT c FROM Complex c WHERE c.status = :status " +
            "AND (c.complexName LIKE %:keyword% OR c.address LIKE %:keyword% " +
            "OR c.nameContact LIKE %:keyword% OR c.phoneContact LIKE %:keyword%)")
    Page<Complex> findByStatusAndKeyword(@Param("status") int status,
                                         @Param("keyword") String keyword,
                                         Pageable pageable);

    @Query("SELECT c FROM Complex c WHERE c.status = :status " +
            "AND c.createdAt BETWEEN :startDate AND :endDate")
    Page<Complex> findByStatusAndCreatedDateRange(@Param("status") int status,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate,
                                                  Pageable pageable);

    @Query("SELECT c FROM Complex c WHERE c.status = :status " +
            "AND (c.complexName LIKE %:keyword% OR c.address LIKE %:keyword% OR c.nameContact LIKE %:keyword% OR c.phoneContact LIKE %:keyword%) " +
            "AND c.createdAt BETWEEN :startDate AND :endDate")
    Page<Complex> findByStatusAndKeywordAndCreatedDateRange(@Param("status") int status,
                                                            @Param("keyword") String keyword,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate,
                                                            Pageable pageable);

    List<Complex> findAllByStatusAndIdIn(int status, List<String> ids);
}
