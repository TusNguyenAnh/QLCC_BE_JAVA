package com.mbs.qlcc.usecases.output.Organization;

import com.mbs.qlcc.entities.Organization.OrgBuilding;
import com.mbs.qlcc.usecases.response.Organization.IOrgBuildingResponse;

import java.util.List;

public interface IOrgBuildingDsGateway {
    List<OrgBuilding> findByOrgId(String orgId);

    List<String> getBuildingIdsByParentOrgId(String parentOrgId);

    void store(List<OrgBuilding> orgBuildings);

    void deleteByOrgId(String orgId);

    List<IOrgBuildingResponse> findOrgsByAllBuildings(List<String> buildingIds, Integer buildingCount);
}
