package com.mbs.qlcc.dto.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBuildingRequest {
    private String buildingName;
    private Float financialRatio;
}
