package com.mbs.qlcc.adapters.db.Organization;

import com.mbs.qlcc.usecases.response.Organization.IOrgBuildingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for OrgBuilding (Junction Table)
 */
@Repository
public interface JpaOrgBuildingRepository extends JpaRepository<OrgBuildingDataMapper, String> {
    List<OrgBuildingDataMapper> findByOrgIdAndDeletedAtIsNull(String orgId);

    void deleteByOrgIdAndDeletedAtIsNull(String orgId);

    @Query(value = "SELECT DISTINCT ob.building_id FROM org_building ob " +
            "JOIN organization o ON ob.org_id = o.id " +
            "WHERE o.parent_org_id = :parentOrgId",
            nativeQuery = true)
    List<String> findBuildingIdsByParentOrgId(@Param("parentOrgId") String parentOrgId);

    @Query(value = "SELECT ob.org_id AS orgId, o.org_name AS orgName, o.level AS level " +
            "FROM org_building ob " +
            "JOIN organization o ON ob.org_id = o.id " +
            "WHERE ob.building_id IN :buildingIds " +
            "GROUP BY ob.org_id, o.org_name, o.level " +
            "HAVING COUNT(DISTINCT ob.building_id) = :buildingCount ",
            nativeQuery = true)
    List<IOrgBuildingResponse> findOrgsByAllBuildings(
            @Param("buildingIds") List<String> buildingIds,
            @Param("buildingCount") Integer buildingCount);

    List<OrgBuildingDataMapper> findByBuildingIdAndDeletedAtIsNull(String buildingId);
}
