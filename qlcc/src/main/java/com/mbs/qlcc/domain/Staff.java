package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "staffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "complex_id", nullable = false)
    String complexId;

    @Column(name = "fullname", nullable = false)
    String fullname;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @Column(name = "status")
    int status; // 0: Active, 1: Inactive

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
}
