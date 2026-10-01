package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "priority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "priority_name", nullable = false)
    private String priorityName;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @Column(name = "weight")
    private Integer weight = 1;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
