package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "workflow")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "workflow_name", nullable = false)
    private String workflowName;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private int status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private Set<WorkflowStep> workflowSteps;

    @OneToMany(mappedBy = "workflow")
    private List<TaskType> taskType;
}
