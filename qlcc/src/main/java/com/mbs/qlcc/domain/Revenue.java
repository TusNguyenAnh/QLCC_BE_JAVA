package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "revenues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "building_id")
    private String buildingId;

    @Column(name = "apartment_id")
    private String apartmentId;

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

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved")
    private Integer approved;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
