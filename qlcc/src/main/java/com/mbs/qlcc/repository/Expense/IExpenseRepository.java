package com.mbs.qlcc.repository.Expense;

import com.mbs.qlcc.domain.Expense;
import com.mbs.qlcc.dto.response.Expense.ICountExpenseResponse;
import com.mbs.qlcc.dto.response.Expense.IExpenseFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, String> {
    @Query(value = "SELECT e.id as id, e.category as category, " +
            "e.total_amount as amount, e.description as description, e.pay_date as payDate " +
            "FROM expenses e " +
            "JOIN expense_detail ed ON e.id = ed.expense_id " +
            "WHERE e.complex_id = :complexId " +
            "AND e.type = :type " +
            "AND (ed.building_id IN (:buildingId)) " +
            "AND (:checkCategory IS NULL OR e.category IN (:category)) " +
            "AND (:proposedFrom IS NULL OR e.pay_date >= :proposedFrom) " +
            "AND (:proposedTo IS NULL OR e.pay_date <= :proposedTo) " +
            "GROUP BY e.id, e.category, e.total_amount, e.description, e.pay_date " +
            "HAVING COUNT(DISTINCT ed.building_id) = :buildingCount",
            countQuery = "SELECT COUNT(DISTINCT e.id) FROM expenses e " +
                    "JOIN expense_detail ed ON e.id = ed.expense_id " +
                    "WHERE e.complex_id = :complexId " +
                    "AND e.type = :type " +
                    "AND (ed.building_id IN (:buildingId)) " +
                    "AND (:checkCategory IS NULL OR e.category IN (:category)) " +
                    "AND (:proposedFrom IS NULL OR e.pay_date >= :proposedFrom) " +
                    "AND (:proposedTo IS NULL OR e.pay_date <= :proposedTo) " +
                    "GROUP BY e.id HAVING COUNT(DISTINCT ed.building_id) = :buildingCount",
            nativeQuery = true)
    Page<IExpenseFilterResponse> getByCluster(@Param("complexId") String complexId,
                                              @Param("checkCategory") String checkCategory,
                                              @Param("type") String type,
                                              @Param("buildingId") Object buildingId,
                                              @Param("buildingCount") int buildingCount,
                                              @Param("category") Object category,
                                              @Param("proposedFrom") Object proposedFrom,
                                              @Param("proposedTo") Object proposedTo,
                                              Pageable pageable);


    @Query(value = "SELECT e.id as id, e.category as category, " +
            "ed.amount as amount, e.description as description, e.pay_date as payDate " +
            "FROM expenses e " +
            "JOIN expense_detail ed ON e.id = ed.expense_id " +
            "WHERE e.complex_id = :complexId " +
            "AND (ed.building_id IN (:buildingId)) " +
            "AND (:checkCategory IS NULL OR e.category IN (:category)) " +
            "AND (:proposedFrom IS NULL OR e.pay_date >= :proposedFrom) " +
            "AND (:proposedTo IS NULL OR e.pay_date <= :proposedTo) ", nativeQuery = true)
    Page<IExpenseFilterResponse> getByBuilding(@Param("complexId") String complexId,
                                               @Param("checkCategory") String checkCategory,
                                               @Param("buildingId") Object buildingId,
                                               @Param("category") Object category,
                                               @Param("proposedFrom") Object proposedFrom,
                                               @Param("proposedTo") Object proposedTo,
                                               Pageable pageable);

    @Query(value = "SELECT e.id as id, e.category as category, " +
            "ed.amount as amount, b.building_name as description, e.pay_date as payDate,e.task_id as taskId " +
            "FROM expenses e " +
            "JOIN expense_detail ed ON e.id = ed.expense_id " +
            "JOIN buildings b ON ed.building_id = b.id " +
            "WHERE e.id = :expenseId AND e.complex_id = :complexId ", nativeQuery = true)
    List<IExpenseFilterResponse> getExpenseDetail(@Param("complexId") String complexId, @Param("expenseId") String expenseId);

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
    ICountExpenseResponse countByFilters(@Param("complexId") String complexId,
                                         @Param("category") Object category,
                                         @Param("vendor") Object vendor,
                                         @Param("status") Object status,
                                         @Param("approved") Object approved,
                                         @Param("buildingId") Object buildingId,
                                         @Param("proposedFrom") Object proposedFrom,
                                         @Param("proposedTo") Object proposedTo);
}
