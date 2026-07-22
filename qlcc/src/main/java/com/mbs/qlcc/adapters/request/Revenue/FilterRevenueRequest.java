package com.mbs.qlcc.adapters.request.Revenue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRevenueRequest {
    String status;
    Integer approved;
    LocalDateTime proposedFrom;
    LocalDateTime proposedTo;
    String buildingId;
    int pageNumber;
    int pageSize;
}
