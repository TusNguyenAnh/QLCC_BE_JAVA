package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "tasktype_id", nullable = false)
    private String taskTypeId;

    @Column(name = "current_org_id", nullable = false)
    private String currentOrgId;

    @Column(name = "creator", nullable = false)
    private String creator;

    @Column(name = "current_step")
    private Integer currentStep;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    /** Values: 'PENDING', 'APPROVED', 'REJECTED', 'UNFINISHED' */
    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
