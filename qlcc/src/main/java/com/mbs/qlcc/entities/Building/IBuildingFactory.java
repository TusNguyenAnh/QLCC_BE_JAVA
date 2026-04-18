package com.mbs.qlcc.entities.Building;

public interface IBuildingFactory {
    Building create(String complexId, String buildingName, Float financialRatio);
}
