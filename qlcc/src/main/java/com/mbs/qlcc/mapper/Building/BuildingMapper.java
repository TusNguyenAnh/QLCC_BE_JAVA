package com.mbs.qlcc.mapper.Building;

import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.dto.request.Building.CreateBuildingRequest;
import com.mbs.qlcc.dto.response.Building.BuildingResponse;

public class BuildingMapper {

    public static BuildingResponse toResponse(Building building) {
        if (building == null) return null;

        return new BuildingResponse(
                building.getId(),
                building.getComplexId(),
                building.getBuildingName(),
                building.getStatus(),
                building.getFinancialRatio(),
                building.getCreatedAt(),
                building.getUpdatedAt()
        );
    }

    public static Building toEntity(CreateBuildingRequest request, String complexId) {
        if (request == null) return null;

        return Building.builder()
                .complexId(complexId != null ? complexId : request.getComplexId())
                .buildingName(request.getBuildingName())
                .financialRatio(request.getFinancialRatio() != null ? request.getFinancialRatio() : 0.0f)
                .status(0)
                .build();
    }

    private BuildingMapper() {}
}
