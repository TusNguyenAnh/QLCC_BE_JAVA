package com.mbs.qlcc.entities.Building;

import java.time.LocalDateTime;

/**
 * Building Domain Entity
 * Represents a building within a complex
 * Status: 0 = Active, 1 = Inactive (soft delete)
 */
public class Building {
    private String id;                    // UUID
    private String complexId;             // Reference to Complex
    private String buildingName;          // Name of building
    private int status;                   // 0: Active, 1: Inactive (soft delete)
    private Float financialRatio;         // Financial ratio (nullable)
    private LocalDateTime createdAt;      // Creation date
    private LocalDateTime updatedAt;      // Update date
    private LocalDateTime deletedAt;      // Soft delete date

    // Constructors
    public Building() {
    }

    public Building(String complexId, String buildingName, Float financialRatio) {
        this.complexId = complexId;
        this.buildingName = buildingName;
        this.financialRatio = financialRatio;
        this.status = 0;  // Default: Active
        this.createdAt = LocalDateTime.now();
    }

    public Building(String id, String complexId, String buildingName, int status,
                    Float financialRatio, LocalDateTime createdAt, LocalDateTime updatedAt,
                    LocalDateTime deletedAt) {
        this.id = id;
        this.complexId = complexId;
        this.buildingName = buildingName;
        this.status = status;
        this.financialRatio = financialRatio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Float getFinancialRatio() {
        return financialRatio;
    }

    public void setFinancialRatio(Float financialRatio) {
        this.financialRatio = financialRatio;
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

