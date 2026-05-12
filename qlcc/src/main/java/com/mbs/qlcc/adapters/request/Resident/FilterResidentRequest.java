package com.mbs.qlcc.adapters.request.Resident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterResidentRequest {
    private int floor;
    private String buildingId;
    private String aptNumber;
    private String relationship;
    private String order;
    private int pageNumber;
    private int pageSize;
}
