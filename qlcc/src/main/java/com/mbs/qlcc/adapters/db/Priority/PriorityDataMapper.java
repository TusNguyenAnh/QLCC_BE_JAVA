package com.mbs.qlcc.adapters.db.Priority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "priority")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriorityDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "priority_name", nullable = false)
    private String priorityName;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Integer weight = 1;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
