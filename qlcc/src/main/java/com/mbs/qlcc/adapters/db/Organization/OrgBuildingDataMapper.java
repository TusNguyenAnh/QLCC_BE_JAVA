package com.mbs.qlcc.adapters.db.Organization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * JPA OrgBuilding Entity (Junction Table)
 * Maps to 'org_building' table in database
 * Represents many-to-many relationship between Organization and Building
 */
@Entity
@Table(name = "org_building")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBuildingDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @Column(name = "building_id", nullable = false)
    private String buildingId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "org_id", insertable = false, updatable = false)
    private OrganizationDataMapper organization;
}
