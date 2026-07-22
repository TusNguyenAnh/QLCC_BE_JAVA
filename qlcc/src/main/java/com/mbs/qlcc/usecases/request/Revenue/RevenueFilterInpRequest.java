package com.mbs.qlcc.usecases.request.Revenue;

import java.time.LocalDateTime;

public class RevenueFilterInpRequest {
    private String status;
    private Integer approved;
    private LocalDateTime proposedFrom;
    private LocalDateTime proposedTo;
    private String buildingId;

    public RevenueFilterInpRequest(String status, Integer approved, LocalDateTime proposedFrom, LocalDateTime proposedTo, String buildingId) {
        this.status = status;
        this.approved = approved;
        this.proposedFrom = proposedFrom;
        this.proposedTo = proposedTo;
        this.buildingId = buildingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
