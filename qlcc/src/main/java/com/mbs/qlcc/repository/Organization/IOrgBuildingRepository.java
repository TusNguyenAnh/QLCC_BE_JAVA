package com.mbs.qlcc.repository.Organization;

import com.mbs.qlcc.domain.OrgBuilding;
import com.mbs.qlcc.dto.response.Organization.IOrgBuildingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrgBuildingRepository extends JpaRepository<OrgBuilding, String> {

    List<OrgBuilding> findByOrgIdAndDeletedAtIsNull(String orgId);

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

    List<OrgBuilding> findByBuildingIdAndDeletedAtIsNull(String buildingId);
}
