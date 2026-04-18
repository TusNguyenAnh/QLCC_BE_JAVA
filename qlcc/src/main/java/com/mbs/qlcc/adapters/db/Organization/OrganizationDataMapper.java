package com.mbs.qlcc.adapters.db.Organization;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "organization")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDataMapper {
    @Id // Đánh dấu đây là khóa chính
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
    @Column(name = "created_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<OrganizationDataMapper> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_org_id", insertable = false, updatable = false)
    private OrganizationDataMapper parent;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<OrgBuildingDataMapper> buildings;
}
