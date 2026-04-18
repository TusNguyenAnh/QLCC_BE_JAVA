package com.mbs.qlcc.usecases.output.Organization;

import com.mbs.qlcc.entities.Organization.OrgBuilding;

import java.util.List;
import java.util.Map;

/**
 * OrgBuilding Data Store Gateway Interface
 * Defines contract for managing organization-building relationships
 */
public interface IOrgBuildingDsGateway {
    
    /**
     * Find all org-building relationships for an organization
     */
    List<OrgBuilding> findByOrgId(String orgId);
    
    /**
     * Get all building IDs already managed by child organizations
     */
    List<String> getBuildingIdsByParentOrgId(String parentOrgId);
    
    /**
     * Create org-building relationships (bulk insert)
     */
    void store(List<OrgBuilding> orgBuildings);
    
    /**
     * Delete all org-building relationships for an organization
     */
    void deleteByOrgId(String orgId);
    
    /**
     * Find organizations managing all specified buildings
     */
    List<Map<String, Object>> findOrgsByAllBuildings(List<String> buildingIds, Integer buildingCount);
}
