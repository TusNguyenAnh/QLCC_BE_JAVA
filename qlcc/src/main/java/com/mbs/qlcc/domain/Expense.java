package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "type") // chi chung cum || chi theo toa
    private String type;

    @Column(name = "category")
    private String category;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    private Instant payDate;

    @OneToMany(mappedBy = "expense")
    private List<ExpenseDetail> expenseDetails;
}
