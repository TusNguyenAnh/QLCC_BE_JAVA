package com.mbs.qlcc.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Expense {
    private String id;
    private String taskId;
    private String buildingId;
    private String category;
    private String title;
    private BigDecimal originalAmount;
    private BigDecimal amountPaid;
    private String vendor;
    private String status; // 'unpaid', 'partial', 'paid', 'overpaid'
    private String description;
    private String createdBy;
    private String approvedBy;
    private Integer approved;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDeleted;

    public Expense(String taskId, String buildingId, String category, String title, BigDecimal originalAmount, BigDecimal amountPaid, String vendor, String status, String description, String createdBy, String approvedBy, Integer approved, LocalDateTime approvedAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isDeleted) {
        this.taskId = taskId;
        this.buildingId = buildingId;
        this.category = category;
        this.title = title;
        this.originalAmount = originalAmount;
        this.amountPaid = amountPaid;
        this.vendor = vendor;
        this.status = status;
        this.description = description;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.approved = approved;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
    }

    public Expense(String id, String taskId, String buildingId, String category, String title, BigDecimal originalAmount, BigDecimal amountPaid, String vendor, String status, String description, String createdBy, String approvedBy, Integer approved, LocalDateTime approvedAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isDeleted) {
        this.id = id;
        this.taskId = taskId;
        this.buildingId = buildingId;
        this.category = category;
        this.title = title;
        this.originalAmount = originalAmount;
        this.amountPaid = amountPaid;
        this.vendor = vendor;
        this.status = status;
        this.description = description;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.approved = approved;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
