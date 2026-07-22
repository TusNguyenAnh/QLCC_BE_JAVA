package com.mbs.qlcc.adapters.db.Expense;

import com.mbs.qlcc.usecases.response.Expense.ICountExpenseResponse;
import com.mbs.qlcc.usecases.response.Expense.IExpenseFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaExpenseRepository extends JpaRepository<ExpenseDataMapper, String> {
    List<ExpenseDataMapper> findByTaskId(String taskId);

    @Query(value = "SELECT e.id as id, e.task_id as taskId, e.building_id as buildingId, e.category as category, " +
            "e.title as title, e.original_amount as originalAmount, e.amount_paid as amountPaid, e.description as description, e.vendor as vendor, " +
            "e.status as status, e.created_by as createdBy, e.approved_by as approvedBy, e.approved as approved, e.approved_at as approvedAt " +
            "FROM expenses e " +
            "JOIN task t ON e.task_id = t.id " +
            "WHERE t.complex_id = :complexId " +
            "AND (:category IS NULL OR e.category = :category) " +
            "AND (:vendor IS NULL OR e.vendor LIKE %:vendor% ) " +
            "AND (:buildingId IS NULL OR e.building_id = :buildingId) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND (:approved IS NULL OR e.approved = :approved) " +
            "AND (:proposedFrom IS NULL OR e.approved_at >= :proposedFrom) " +
            "AND (:proposedTo IS NULL OR e.approved_at <= :proposedTo) ", nativeQuery = true)
    Page<IExpenseFilterResponse> getByFilters(@Param("complexId") String complexId, @Param("category") Object category, @Param("vendor") Object vendor,
                                              @Param("status") Object status, @Param("approved") Object approved,
                                              @Param("buildingId") Object buildingId, @Param("proposedFrom") Object proposedFrom,
                                              @Param("proposedTo") Object proposedTo, Pageable pageable);

    @Query(value = "SELECT SUM(e.amount_paid) AS paid, " +
            "SUM(e.original_amount) AS totalExpect " +
            "FROM expenses e " +
            "JOIN task t ON e.task_id = t.id " +
            "WHERE t.complex_id = :complexId " +
            "AND (:category IS NULL OR e.category = :category) " +
            "AND (:vendor IS NULL OR e.vendor LIKE %:vendor% ) " +
            "AND (:buildingId IS NULL OR e.building_id = :buildingId) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND (:approved IS NULL OR e.approved = :approved) " +
            "AND (:proposedFrom IS NULL OR e.approved_at >= :proposedFrom) " +
            "AND (:proposedTo IS NULL OR e.approved_at <= :proposedTo) ", nativeQuery = true)
    ICountExpenseResponse countByFilters(@Param("complexId") String complexId, @Param("category") Object category, @Param("vendor") Object vendor,
                                         @Param("status") Object status, @Param("approved") Object approved,
                                         @Param("buildingId") Object buildingId, @Param("proposedFrom") Object proposedFrom,
                                         @Param("proposedTo") Object proposedTo);
}
