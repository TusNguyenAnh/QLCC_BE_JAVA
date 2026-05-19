package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.adapters.db.Authentication.RoleDataMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_step_approver")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepApproverDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_step_id")
    private WorkflowStepDataMapper workflowStepDataMapper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleDataMapper roleDataMapper;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @Column(name = "status")
    private Integer status = 0;
}
