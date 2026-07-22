package com.mbs.qlcc.adapters.db.Task;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskHistoryDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "approver_id", nullable = false)
    private String approverId;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @Column(name = "step_order")
    private Integer stepOrder;

    @Column(name = "action", nullable = false)
    private String action; // 'APPROVED', 'REJECTED','PENDING'

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
