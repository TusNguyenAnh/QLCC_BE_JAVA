package com.mbs.qlcc.adapters.db.Task;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "tasktype_id", nullable = false)
    private String taskTypeId;

    @Column(name = "current_org_id", nullable = false)
    private String currentOrgId;

    @Column(name = "creator", nullable = false)
    private String creator;

    @Column(name = "current_step")
    private Integer currentStep;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status; // 'PENDING', 'APPROVED', 'REJECTED','UNFINISHED'

    @Column(name = "category")
    private String category;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
