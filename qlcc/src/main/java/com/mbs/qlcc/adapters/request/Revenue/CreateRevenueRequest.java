package com.mbs.qlcc.adapters.request.Revenue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRevenueRequest {
    private String taskId;
    private String title;
    private List<String> buildingId;
    private String apartmentId;
    private BigDecimal originalAmount;
    private String status;
    private String description;
    private Integer revenueType;
}
