package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import com.mbs.qlcc.adapters.db.Task.TaskTypeDataMapper;
import jakarta.persistence.*;
import lombok.*;

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
public class WorkflowDataMapper {
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

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @OneToMany(mappedBy = "workflowDataMapper", fetch = FetchType.LAZY)
    private Set<WorkflowStepDataMapper> workflowSteps;

    @OneToMany(mappedBy = "workflow")
    private List<TaskTypeDataMapper> taskType;
}
