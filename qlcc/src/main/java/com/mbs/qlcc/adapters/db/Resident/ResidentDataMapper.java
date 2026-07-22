package com.mbs.qlcc.adapters.db.Resident;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "residents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "gender")
    private int gender; // 0: Male, 1: Female

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "relationship")
    private String relationship;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "cccd", nullable = false)
    private String cccd;

    @Column(name = "status")
    private int status; // 0: Active, 1: Inactive

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "residentDataMapper")
    private List<AptResidentDataMapper> aptResidentDataMapper;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
