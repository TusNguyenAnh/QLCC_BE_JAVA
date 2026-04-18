package com.mbs.qlcc.usecases.response.Building;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BuildingResponse {
    private String id;
    private String complexId;
    private String buildingName;
    private int status;
    private Float financialRatio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BuildingResponse() {
    }

    public BuildingResponse(String complexId, String buildingName, int status, Float financialRatio, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.complexId = complexId;
        this.buildingName = buildingName;
        this.status = status;
        this.financialRatio = financialRatio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BuildingResponse(String id, String complexId, String buildingName, int status, Float financialRatio, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.complexId = complexId;
        this.buildingName = buildingName;
        this.status = status;
        this.financialRatio = financialRatio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
}
