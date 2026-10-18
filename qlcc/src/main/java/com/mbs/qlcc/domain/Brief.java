package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brief")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brief extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(nullable = false)
    private String briefName;

    @Column(nullable = false)
    private String type;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted = false;
}
