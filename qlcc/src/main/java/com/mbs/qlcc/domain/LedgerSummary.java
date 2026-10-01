package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "building_id")
    private String buildingId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "total_in", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalIn = BigDecimal.ZERO;

    @Column(name = "total_out", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalOut = BigDecimal.ZERO;

    @Column(name = "opening_balance", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal openingBalance = BigDecimal.ZERO;

    @Column(name = "closing_balance", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal closingBalance = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted = false;
}
