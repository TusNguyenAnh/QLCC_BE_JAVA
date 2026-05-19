package com.mbs.qlcc.adapters.db.Workflow;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workflow_step")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private WorkflowDataMapper workflowDataMapper;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder = 1;

    @Column(name = "org_level")
    private Integer orgLevel;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @OneToMany(mappedBy = "workflowStepDataMapper")
    private List<WorkflowStepApproverDataMapper> workflowStepApprovers;
}
