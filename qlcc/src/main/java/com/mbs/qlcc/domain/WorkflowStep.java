package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "workflow_step")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStep extends BaseEntity {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "org_level")
    private Integer orgLevel;

    @Column(name = "description")
    private String description;

    @Column
    private String moduleCode;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "workflowStep")
    private Set<WorkflowStepApprover> workflowStepApprovers;
}
