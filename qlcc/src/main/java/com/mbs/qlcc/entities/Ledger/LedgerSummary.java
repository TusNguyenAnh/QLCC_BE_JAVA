package com.mbs.qlcc.entities.Ledger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LedgerSummary {
    private String id;
    private String complexId;
    private String buildingId;
    private Integer year;
    private Integer month;
    private BigDecimal totalIn;
    private BigDecimal totalOut;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
    boolean isDeleted = false;


    public LedgerSummary(String id, String complexId, String buildingId, Integer year, Integer month, BigDecimal totalIn, BigDecimal totalOut, BigDecimal openingBalance, BigDecimal closingBalance) {
        this.id = id;
        this.complexId = complexId;
        this.buildingId = buildingId;
        this.year = year;
        this.month = month;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
    }

    public LedgerSummary(String complexId, String buildingId, Integer year, Integer month, BigDecimal totalIn, BigDecimal totalOut, BigDecimal openingBalance, BigDecimal closingBalance, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isDeleted) {
        this.complexId = complexId;
        this.buildingId = buildingId;
        this.year = year;
        this.month = month;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
    }

    public LedgerSummary(String id, String complexId, String buildingId, Integer year, Integer month, BigDecimal totalIn, BigDecimal totalOut, BigDecimal openingBalance, BigDecimal closingBalance, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isDeleted) {
        this.id = id;
        this.complexId = complexId;
        this.buildingId = buildingId;
        this.year = year;
        this.month = month;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
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

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(BigDecimal totalIn) {
        this.totalIn = totalIn;
    }

    public BigDecimal getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(BigDecimal totalOut) {
        this.totalOut = totalOut;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
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
