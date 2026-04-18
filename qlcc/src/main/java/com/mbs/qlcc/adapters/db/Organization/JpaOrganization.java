package com.mbs.qlcc.adapters.db.Organization;

import com.mbs.qlcc.adapters.db.Complex.ComplexDataMapper;
import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.Organization.OrgBuilding;
import com.mbs.qlcc.entities.Organization.Organization;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.output.Organization.IOrganizationDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Organization Gateway Implementation
 * Adapts JPA repository to organization gateway interface
 */
@Component
@RequiredArgsConstructor
public class JpaOrganization implements IOrganizationDsGateway {

    private final JpaOrganizationRepository jpaOrganizationRepository;

    @Override
    public PageResponse<Organization> show(String complexId, int page, int perPage) {
        Pageable pageable = PageRequest.of(
                page,
                perPage,
                Sort.Direction.ASC,
                "createdAt"
        );

        Page<OrganizationDataMapper> jpaOrgs = jpaOrganizationRepository
                .findByComplexIdAndParentOrgIdIsNullAndStatusEquals(complexId, "0", pageable);

        return new PageResponse<Organization>(
                jpaOrgs.getContent().stream().map(this::mapToEntity).toList(),
                jpaOrgs.getNumber(),
                jpaOrgs.getSize(),
                jpaOrgs.getTotalElements(),
                jpaOrgs.getTotalPages()
        );
    }

    @Override
    public List<Organization> getAllWithoutDescendants(String parentOrgId, String complexId) {
        List<OrganizationDataMapper> jpaOrgs = jpaOrganizationRepository
                .findAllWithoutDescendants(parentOrgId, complexId);
        return jpaOrgs.stream()
                .map(this::mapToEntitySimple)
                .toList();
    }

    @Override
    public Optional<Organization> getById(String id) {
        return jpaOrganizationRepository.findByIdAndStatusEquals(id, "0")
                .map(this::mapToEntity);
    }

    @Override
    public Integer getTopLevel(String complexId) {
        Integer maxLevel = jpaOrganizationRepository.getMaxLevel(complexId);
        return maxLevel != null ? maxLevel : 0;
    }

    @Override
    public Organization store(Organization organization) {
        OrganizationDataMapper mapper = OrganizationDataMapper.builder()
                .orgCode(organization.getOrgCode())
                .orgName(organization.getOrgName())
                .complexId(organization.getComplexId())
                .parentOrgId(organization.getParentOrgId())
                .description(organization.getDescription())
                .level(organization.getLevel())
                .status(organization.getStatus())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .build();
        OrganizationDataMapper saved = jpaOrganizationRepository.save(mapper);
        return mapToEntity(saved);
    }

    @Override
    public Organization update(Organization organization) {
        Optional<OrganizationDataMapper> org = jpaOrganizationRepository.findById(organization.getId());
        if (org.isEmpty()) {
            throw new AppException(ErrorCode.ORG_NAME_NOT_FOUND);
        }
        OrganizationDataMapper orgUpdate = org.get();
        orgUpdate.setOrgName(organization.getOrgName());
        orgUpdate.setOrgCode(organization.getOrgCode());
        orgUpdate.setDescription(organization.getDescription());
        orgUpdate.setUpdatedAt(LocalDateTime.now());
        orgUpdate.setParentOrgId(organization.getParentOrgId());
        orgUpdate.setLevel(organization.getLevel());
        return mapToEntity(jpaOrganizationRepository.save(orgUpdate));
    }

    @Override
    public void delete(List<String> organizationIds) {
        organizationIds.forEach(id -> {
            Optional<OrganizationDataMapper> org = jpaOrganizationRepository.findById(id);
            org.ifPresent(jpaOrg -> {
                jpaOrg.setStatus("1");// Soft delete
                jpaOrg.setDeletedAt(LocalDateTime.now());
                jpaOrganizationRepository.save(jpaOrg);
            });
        });
    }

    @Override
    public boolean existsRootOrg(String complexId) {
        return jpaOrganizationRepository.existsByComplexIdAndParentOrgIdIsNullAndStatusEquals(complexId, "0");
    }

    @Override
    public boolean existsOrgCode(String complexId, String orgCode) {
        return jpaOrganizationRepository.existsByComplexIdAndOrgCodeAndStatusEquals(complexId, orgCode, "0");
    }

    @Override
    public List<Organization> findByComplexIdAndStatus(String complexId, String status) {
        List<OrganizationDataMapper> jpaOrgs = jpaOrganizationRepository.findByComplexIdAndStatusEquals(complexId, status);
        return jpaOrgs.stream()
                .map(this::mapToEntity)
                .toList();
    }

    // Mapping methods
    private Organization mapToEntity(OrganizationDataMapper jpaOrg) {
        Organization org = new Organization();
        if (jpaOrg.getChildren() != null) {
            org.setChild(
                    jpaOrg.getChildren().stream()
                            .map(this::mapToEntity) // 👈 recursion
                            .toList()
            );
        } else {
            org.setChild(List.of());
        }

        if (jpaOrg.getBuildings() != null) {
            org.setBuildings(
                    jpaOrg.getBuildings().stream()
                            .map(OrgBuildingDataMapper::getBuildingId)
                            .toList()
            );
        }
        org.setId(jpaOrg.getId());
        org.setOrgCode(jpaOrg.getOrgCode());
        org.setOrgName(jpaOrg.getOrgName());
        org.setComplexId(jpaOrg.getComplexId());
        org.setParentOrgId(jpaOrg.getParentOrgId());
        org.setDescription(jpaOrg.getDescription());
        org.setStatus(jpaOrg.getStatus());
        org.setLevel(jpaOrg.getLevel());

        return org;
    }

    private Organization mapToEntitySimple(OrganizationDataMapper jpaOrg) {
        Organization org = new Organization();
        org.setId(jpaOrg.getId());
        org.setOrgCode(jpaOrg.getOrgCode());
        org.setOrgName(jpaOrg.getOrgName());

        return org;
    }
}
