package com.mbs.qlcc.usecases.request.Revenue;

import java.math.BigDecimal;
import java.util.List;

public class CreateRevenueInpRequest {
    private String taskId;
    private String title;
    private List<String> buildingId;
    private String apartmentId;
    private BigDecimal originalAmount;
    private String status;
    private String description;
    private Integer revenueType;

    public CreateRevenueInpRequest(String taskId, String title, List<String> buildingId, String apartmentId, BigDecimal originalAmount, String status, String description, Integer revenueType) {
        this.taskId = taskId;
        this.title = title;
        this.buildingId = buildingId;
        this.apartmentId = apartmentId;
        this.originalAmount = originalAmount;
        this.status = status;
        this.description = description;
        this.revenueType = revenueType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(List<String> buildingId) {
        this.buildingId = buildingId;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRevenueType() {
        return revenueType;
    }

    public void setRevenueType(Integer revenueType) {
        this.revenueType = revenueType;
    }
}
