package com.mbs.qlcc.entities.Revenue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IRevenueFactory {
    Revenue createRevenue(String taskId, String buildingId, String apartmentId, String title, BigDecimal originalAmount, BigDecimal amountPaid,
                          String status, String description, String createdBy, String approvedBy, Integer approved, LocalDateTime approvedAt);
}
