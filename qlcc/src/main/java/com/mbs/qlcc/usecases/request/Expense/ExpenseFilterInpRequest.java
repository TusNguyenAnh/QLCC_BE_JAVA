package com.mbs.qlcc.usecases.request.Expense;

import java.time.LocalDateTime;

public class ExpenseFilterInpRequest {
    private String category;
    private String status;
    private String vendor;
    private Integer approved;
    private LocalDateTime proposedFrom;
    private LocalDateTime proposedTo;
    private String buildingId;

    public ExpenseFilterInpRequest(String category, String status, String vendor, Integer approved, LocalDateTime proposedFrom, LocalDateTime proposedTo, String buildingId) {
        this.category = category;
        this.status = status;
        this.vendor = vendor;
        this.approved = approved;
        this.proposedFrom = proposedFrom;
        this.proposedTo = proposedTo;
        this.buildingId = buildingId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public LocalDateTime getProposedFrom() {
        return proposedFrom;
    }

    public void setProposedFrom(LocalDateTime proposedFrom) {
        this.proposedFrom = proposedFrom;
    }

    public LocalDateTime getProposedTo() {
        return proposedTo;
    }

    public void setProposedTo(LocalDateTime proposedTo) {
        this.proposedTo = proposedTo;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
}
