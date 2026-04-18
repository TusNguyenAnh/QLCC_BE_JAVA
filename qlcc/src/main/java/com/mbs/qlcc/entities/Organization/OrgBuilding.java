package com.mbs.qlcc.entities.Organization;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * OrgBuilding Domain Entity (Junction Table)
 * Represents the many-to-many relationship between Organization and Building
 */
public class OrgBuilding {
    private String id;
    private String orgId;
    private String buildingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // No-args Constructor
    public OrgBuilding() {
    }

    // Constructor for create operation
    public OrgBuilding(String orgId, String buildingId) {
        this.orgId = orgId;
        this.buildingId = buildingId;
    }

    // Full Constructor
    public OrgBuilding(String id, String orgId, String buildingId) {
        this.id = id;
        this.orgId = orgId;
        this.buildingId = buildingId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
