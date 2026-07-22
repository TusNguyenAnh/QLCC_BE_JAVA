package com.mbs.qlcc.adapters.db.Expense;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "building_id")
    private String buildingId;

    @Column(name = "category")
    private String category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "original_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal originalAmount;

    @Column(name = "amount_paid", nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "status", nullable = false)
    private String status; // 'unpaid', 'partial', 'paid', 'overpaid'

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved")
    private Integer approved;

    @Column(name = "approved_at")
    LocalDateTime approvedAt;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
