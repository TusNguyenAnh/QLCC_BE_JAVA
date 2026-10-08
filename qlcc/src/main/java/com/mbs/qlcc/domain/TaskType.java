package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted = false;
}
