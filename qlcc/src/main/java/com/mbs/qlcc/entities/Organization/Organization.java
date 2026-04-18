package com.mbs.qlcc.entities.Organization;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Organization Domain Entity
 * Represents a hierarchical organization within a complex
 * Status: 0 = Active, 1 = Inactive (Soft Delete)
 */
public class Organization {
    private String id;
    private String orgCode;
    private String orgName;
    private String complexId;
    private String parentOrgId;  // null for root organization
    private String description;
    private String status;  // "0" = Active, "1" = Inactive
    private int level;  // 1-3, calculated from parent
    private List<Organization> child;
    private List<String> buildings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // No-args Constructor
    public Organization() {
    }

    // Constructor for create operation


    public Organization(String orgCode, String orgName, String complexId, String parentOrgId, String description, String status, int level) {
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.status = status;
        this.level = level;
    }

    public Organization(String orgCode, String orgName, String complexId, String parentOrgId, String description, String status, int level, List<Organization> child, List<String> buildings) {
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.status = status;
        this.level = level;
        this.child = child;
        this.buildings = buildings;
    }

    public Organization(String orgCode, String orgName, String complexId, String parentOrgId, String description, String status, int level, List<Organization> child, List<String> buildings, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.status = status;
        this.level = level;
        this.child = child;
        this.buildings = buildings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Organization(String id, String orgCode, String orgName, String complexId, String parentOrgId, String description, String status, int level, List<Organization> child, List<String> buildings, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.status = status;
        this.level = level;
        this.child = child;
        this.buildings = buildings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public List<String> getOrgBuildings() {
        return buildings;
    }

    public void setBuildings(List<String> buildings) {
        this.buildings = buildings;
    }

    public List<Organization> getChild() {
        return child;
    }

    public void setChild(List<Organization> child) {
        this.child = child;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
