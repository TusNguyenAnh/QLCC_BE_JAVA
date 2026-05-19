package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workflow")
@Data
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
    private List<WorkflowStepDataMapper> workflowSteps;
}
