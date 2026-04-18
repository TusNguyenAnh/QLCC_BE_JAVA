package com.mbs.qlcc.adapters.db.Organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Spring Data JPA Repository for OrgBuilding (Junction Table)
 */
@Repository
public interface JpaOrgBuildingRepository extends JpaRepository<OrgBuildingDataMapper, String> {
    
    /**
     * Find all buildings managed by an organization
     */
    List<OrgBuildingDataMapper> findByOrgIdAndDeletedAtIsNull(String orgId);
    
    /**
     * Delete all building relationships for an organization
     */
    void deleteByOrgIdAndDeletedAtIsNull(String orgId);
    
    /**
     * Get all building IDs already managed by children organizations
     */
    @Query(value = "SELECT DISTINCT ob.building_id FROM org_building ob " +
            "JOIN organization o ON ob.org_id = o.id " +
            "WHERE o.parent_org_id = :parentOrgId AND o.status = '0' AND ob.deleted_at IS NULL",
            nativeQuery = true)
    List<String> findBuildingIdsByParentOrgId(@Param("parentOrgId") String parentOrgId);
    
    /**
     * Find organizations managing all specified buildings
     * Returns organizations that manage ALL buildings in the list
     */
    @Query(value = "SELECT o.id, o.org_code, o.org_name, o.level FROM org_building ob " +
            "JOIN organization o ON ob.org_id = o.id " +
            "WHERE ob.building_id IN (:buildingIds) AND o.status = '0' AND ob.deleted_at IS NULL " +
            "GROUP BY o.id, o.org_code, o.org_name, o.level " +
            "HAVING COUNT(DISTINCT ob.building_id) = :buildingCount",
            nativeQuery = true)
    List<Map<String, Object>> findOrgsByAllBuildings(
            @Param("buildingIds") List<String> buildingIds,
            @Param("buildingCount") Integer buildingCount);
    
    /**
     * Find all org_building records by building ID
     */
    List<OrgBuildingDataMapper> findByBuildingIdAndDeletedAtIsNull(String buildingId);
}
