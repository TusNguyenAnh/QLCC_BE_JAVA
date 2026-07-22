package com.mbs.qlcc.entities.Revenue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RevenueFactory implements IRevenueFactory {
    @Override
    public Revenue createRevenue(String taskId, String buildingId, String apartmentId, String title, BigDecimal originalAmount, BigDecimal amountPaid, String status, String description, String createdBy, String approvedBy, Integer approved, LocalDateTime approvedAt) {
        return new Revenue(taskId, buildingId, apartmentId, title, originalAmount, amountPaid, status, description, createdBy, approvedBy, approved, approvedAt, LocalDateTime.now(), LocalDateTime.now(), null, false);
    }
}
