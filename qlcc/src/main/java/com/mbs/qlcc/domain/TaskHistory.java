package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskHistory {
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

    /** Values: 'APPROVED', 'REJECTED', 'PENDING', 'UNFINISHED' */
    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
