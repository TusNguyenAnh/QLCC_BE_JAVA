package com.mbs.qlcc.adapters.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBuildingRequest {
    private String complexId;
    private String buildingName;
    private Float financialRatio;
}
