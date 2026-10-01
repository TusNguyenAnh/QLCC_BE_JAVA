package com.mbs.qlcc.repository.Revenue;

import com.mbs.qlcc.domain.Revenue;
import com.mbs.qlcc.dto.response.Revenue.ICountRevenueResponse;
import com.mbs.qlcc.dto.response.Revenue.IRevenueFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRevenueRepository extends JpaRepository<Revenue, String> {
    List<Revenue> findByTaskId(String taskId);

    @Query(value = "SELECT r.id as id, r.task_id as taskId, r.building_id as buildingId, r.apartment_id as apartmentId, " +
            "r.title as title, r.original_amount as originalAmount, r.amount_paid as amountPaid, r.description as description, " +
            "r.status as status, r.created_by as createdBy, r.approved_by as approvedBy, r.approved as approved, r.approved_at as approvedAt " +
            "FROM revenues r " +
            "JOIN task t ON r.task_id = t.id " +
            "WHERE t.complex_id = :complexId " +
            "AND (:buildingId IS NULL OR r.building_id = :buildingId) " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND (:approved IS NULL OR r.approved = :approved) " +
            "AND (:proposedFrom IS NULL OR r.approved_at >= :proposedFrom) " +
            "AND (:proposedTo IS NULL OR r.approved_at <= :proposedTo) ", nativeQuery = true)
    Page<IRevenueFilterResponse> getByFilters(@Param("complexId") String complexId,
                                              @Param("status") Object status,
                                              @Param("approved") Object approved,
                                              @Param("buildingId") Object buildingId,
                                              @Param("proposedFrom") Object proposedFrom,
                                              @Param("proposedTo") Object proposedTo,
                                              Pageable pageable);

    @Query(value = "SELECT SUM(r.amount_paid) AS paid, " +
            "SUM(r.original_amount) AS totalExpect " +
            "FROM revenues r " +
            "JOIN task t ON r.task_id = t.id " +
            "WHERE t.complex_id = :complexId " +
            "AND (:buildingId IS NULL OR r.building_id = :buildingId) " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND (:approved IS NULL OR r.approved = :approved) " +
            "AND (:proposedFrom IS NULL OR r.approved_at >= :proposedFrom) " +
            "AND (:proposedTo IS NULL OR r.approved_at <= :proposedTo) ", nativeQuery = true)
    ICountRevenueResponse countByFilters(@Param("complexId") String complexId,
                                         @Param("status") Object status,
                                         @Param("approved") Object approved,
                                         @Param("buildingId") Object buildingId,
                                         @Param("proposedFrom") Object proposedFrom,
                                         @Param("proposedTo") Object proposedTo);
}
