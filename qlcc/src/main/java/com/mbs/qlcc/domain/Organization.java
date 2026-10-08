package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organization")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "complex_id")
    private String complexId;

    @Column(name = "parent_org_id")
    private String parentOrgId;

    @Column(name = "description")
    private String description;

    @Column
    private int level;

    @Column
    private String status;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Organization> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_org_id", insertable = false, updatable = false)
    private Organization parent;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<OrgBuilding> orgBuildings;
}
