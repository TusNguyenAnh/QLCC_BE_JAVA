package com.mbs.qlcc.adapters.db.Resident;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "apt_res",uniqueConstraints = {@UniqueConstraint(columnNames = {"apt_id", "resident_id"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AptResidentDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apt_id", nullable = false)
    private ApartmentDataMapper apartmentDataMapper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private ResidentDataMapper residentDataMapper;

    @Column(name = "status")
    private int status; // 0: Active, 1: Inactive

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
