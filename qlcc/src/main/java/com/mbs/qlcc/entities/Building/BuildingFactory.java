package com.mbs.qlcc.entities.Building;

import java.time.LocalDateTime;
import java.util.UUID;

public class BuildingFactory implements IBuildingFactory {

    @Override
    public Building create(String complexId, String buildingName, Float financialRatio) {
        Building building = new Building();
        building.setId(UUID.randomUUID().toString());
        building.setComplexId(complexId);
        building.setBuildingName(buildingName);
        building.setStatus(0);  // Default: Active
        building.setFinancialRatio(financialRatio);
        building.setCreatedAt(LocalDateTime.now());
        building.setUpdatedAt(LocalDateTime.now());
        building.setDeletedAt(null);
        return building;
    }
}
