package com.mbs.qlcc.adapters.db.Apartment;

import com.mbs.qlcc.adapters.db.Building.BuildingDataMapper;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apartments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"building_id", "apt_number"})
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDataMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private BuildingDataMapper buildingDataMapper;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "apt_number", length = 20, nullable = false)
    private String aptNumber;

    @Column(name = "gross_area", nullable = false, precision = 10, scale = 2)
    private BigDecimal grossArea;

    @Column(name = "carpet_area", nullable = false, precision = 10, scale = 2)
    private BigDecimal carpetArea;

    @Column(name = "coefficient", nullable = false)
    private BigDecimal coefficient;

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
