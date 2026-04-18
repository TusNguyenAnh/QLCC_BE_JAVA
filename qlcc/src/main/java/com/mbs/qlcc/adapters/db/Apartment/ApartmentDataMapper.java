package com.mbs.qlcc.adapters.db.Apartment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apartments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"building_id", "apt_number"})
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDataMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "building_id", nullable = false)
    private String buildingId;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "apt_number", length = 20, nullable = false)
    private String aptNumber;

    @Column(name = "gross_area", nullable = false)
    private Double grossArea;

    @Column(name = "carpet_area", nullable = false)
    private Double carpetArea;

    @Column(name = "coefficient", nullable = false)
    private Double coefficient;

    @Column(name = "apt_type", length = 100, nullable = false)
    private String aptType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
