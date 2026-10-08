package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "buildings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"complex_id", "building_name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_id", nullable = false)
    private String complexId;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "financial_ratio")
    private Float financialRatio;

    @OneToMany(mappedBy = "building")
    private List<Apartment> apartments;
}
