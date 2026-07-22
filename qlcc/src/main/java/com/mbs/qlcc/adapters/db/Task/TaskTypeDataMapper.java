package com.mbs.qlcc.adapters.db.Task;

import com.mbs.qlcc.adapters.db.Priority.PriorityDataMapper;
import com.mbs.qlcc.adapters.db.Workflow.WorkflowDataMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTypeDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private WorkflowDataMapper workflow;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private PriorityDataMapper priority;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
