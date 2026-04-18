package com.mbs.qlcc.adapters.db.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "buildings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"complex_id", "building_name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDataMapper {
    @Id
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "financial_ratio")
    private Float financialRatio;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
