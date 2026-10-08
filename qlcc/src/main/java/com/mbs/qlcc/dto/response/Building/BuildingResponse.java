package com.mbs.qlcc.dto.response.Building;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

public class BuildingResponse {
    private String id;
    private String complexId;
    private String buildingName;
    private int status;
    private Float financialRatio;
    private Instant createdAt;
    private Instant updatedAt;

    public BuildingResponse() {
    }

    public BuildingResponse(String complexId, String buildingName, int status, Float financialRatio, Instant createdAt, Instant updatedAt) {
        this.complexId = complexId;
        this.buildingName = buildingName;
        this.status = status;
        this.financialRatio = financialRatio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BuildingResponse(String id, String complexId, String buildingName, int status, Float financialRatio, Instant createdAt, Instant updatedAt) {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
