package com.mbs.qlcc.usecases.response.Revenue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IRevenueFilterResponse {
    String getId();

    String getTaskId();

    String getBuildingId();

    String getApartmentId();

    String getTitle();

    BigDecimal getOriginalAmount();

    BigDecimal getAmountPaid();

    String getStatus();

    String getDescription();

    String getCreatedBy();

    String getApprovedBy();

    Integer getApproved();

    LocalDateTime getApprovedAt();
}
