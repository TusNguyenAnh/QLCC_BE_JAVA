package com.mbs.qlcc.adapters.db.Organization;

import com.mbs.qlcc.adapters.db.User.UserDataMapper;
import com.mbs.qlcc.entities.Organization.OrgBuilding;
import com.mbs.qlcc.entities.Organization.Organization;
import com.mbs.qlcc.usecases.output.Organization.IOrgBuildingDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * OrgBuilding Gateway Implementation
 * Adapts JPA repository to org-building gateway interface
 */
@Component
@RequiredArgsConstructor
public class JpaOrgBuilding implements IOrgBuildingDsGateway {

    private final JpaOrgBuildingRepository jpaOrgBuildingRepository;

    @Override
    public List<OrgBuilding> findByOrgId(String orgId) {
        List<OrgBuildingDataMapper> jpaOrgBuildings = jpaOrgBuildingRepository.findByOrgIdAndDeletedAtIsNull(orgId);
        return jpaOrgBuildings.stream()
                .map(this::mapToEntity)
                .toList();
    }

    @Override
    public List<String> getBuildingIdsByParentOrgId(String parentOrgId) {
        return jpaOrgBuildingRepository.findBuildingIdsByParentOrgId(parentOrgId);
    }

    @Override
    public void store(List<OrgBuilding> orgBuildings) {
        List<OrgBuildingDataMapper> jpaOrgBuildings = orgBuildings.stream()
                .map((req -> OrgBuildingDataMapper.builder()
                        .orgId(req.getOrgId())
                        .buildingId(req.getBuildingId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()))
                .toList();
        jpaOrgBuildingRepository.saveAll(jpaOrgBuildings);
    }

    @Override
    public void deleteByOrgId(String orgId) {
        jpaOrgBuildingRepository.deleteByOrgIdAndDeletedAtIsNull(orgId);
    }

    @Override
    public List<Map<String, Object>> findOrgsByAllBuildings(List<String> buildingIds, Integer buildingCount) {
        return jpaOrgBuildingRepository.findOrgsByAllBuildings(buildingIds, buildingCount);
    }

    private OrgBuilding mapToEntity(OrgBuildingDataMapper orgBd) {
        return new OrgBuilding(orgBd.getId(), orgBd.getOrgId(), orgBd.getBuildingId());
    }
}
