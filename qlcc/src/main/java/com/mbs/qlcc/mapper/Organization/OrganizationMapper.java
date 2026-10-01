package com.mbs.qlcc.mapper.Organization;

import com.mbs.qlcc.domain.Organization;
import com.mbs.qlcc.dto.response.Organization.OrganizationResponse;
import com.mbs.qlcc.dto.response.Organization.OrganizationWithoutChildResponse;

import java.util.List;

public class OrganizationMapper {

    public static OrganizationResponse toResponse(Organization org) {
        if (org == null) return null;

        OrganizationResponse response = new OrganizationResponse(
                org.getId(),
                org.getOrgCode(),
                org.getOrgName(),
                org.getComplexId(),
                org.getParentOrgId(),
                org.getDescription(),
                org.getStatus(),
                org.getLevel()
        );

        // Map building ids from OrgBuilding list
        if (org.getOrgBuildings() != null) {
            response.setBuildingIds(
                    org.getOrgBuildings().stream()
                            .map(ob -> ob.getBuildingId())
                            .toList()
            );
        }

        // Recursive mapping for children
        if (org.getChildren() != null) {
            response.setChildren(
                    org.getChildren().stream()
                            .map(OrganizationMapper::toResponse)
                            .toList()
            );
        } else {
            response.setChildren(List.of());
        }

        return response;
    }

    public static OrganizationWithoutChildResponse toSimpleResponse(Organization org) {
        if (org == null) return null;
        return new OrganizationWithoutChildResponse(
                org.getId(),
                org.getOrgCode(),
                org.getOrgName()
        );
    }

    private OrganizationMapper() {}
}
