package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.domain.OrgBuilding;
import com.mbs.qlcc.domain.Organization;
import com.mbs.qlcc.dto.request.Organization.CreateOrganizationRequest;
import com.mbs.qlcc.dto.request.Organization.UpdateOrganizationRequest;
import com.mbs.qlcc.dto.response.Organization.OrganizationResponse;
import com.mbs.qlcc.dto.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Organization.OrganizationMapper;
import com.mbs.qlcc.repository.Building.IBuildingRepository;
import com.mbs.qlcc.repository.Organization.IOrgBuildingRepository;
import com.mbs.qlcc.repository.Organization.IOrganizationRepository;
import com.mbs.qlcc.service.IOrganizationService;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationServiceImpl implements IOrganizationService {

    IOrganizationRepository organizationRepository;
    IOrgBuildingRepository orgBuildingRepository;
    IBuildingRepository buildingRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrganizationResponse> show(String complexId, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.ASC, "createdAt");
        Page<Organization> result = organizationRepository
                .findByComplexIdAndParentOrgIdIsNullAndStatusEquals(complexId, "0", pageable);

        return new PageResponse<>(
                result.getContent().stream().map(OrganizationMapper::toResponse).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationResponse findById(String id) {
        return organizationRepository.findByIdAndStatusEquals(id, "0")
                .map(OrganizationMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId) {
        return organizationRepository.findAllWithoutDescendants(parentOrgId, complexId).stream()
                .map(OrganizationMapper::toSimpleResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrganizationResponse create(String complexId, CreateOrganizationRequest request) {
        int level = 1;
        String parentOrgId = request.getParentOrgId().equals("null") ? null : request.getParentOrgId();

        if (!request.getParentOrgId().equals("null") && !request.getParentOrgId().isEmpty()) {
            Organization parent = organizationRepository.findByIdAndStatusEquals(request.getParentOrgId(), "0")
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            level = parent.getLevel() + 1;

            if (level > 3) {
                throw new AppException(ErrorCode.MAX_ORG_LEVEL);
            }
        } else {
            // Root organization: only one per complex allowed
            if (organizationRepository.existsByComplexIdAndParentOrgIdIsNullAndStatusEquals(complexId, "0")) {
                throw new AppException(ErrorCode.PARENT_ORG_EXISTED);
            }
        }

        if (organizationRepository.existsByComplexIdAndOrgCodeAndStatusEquals(complexId, request.getOrgCode(), "0")) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        Organization org = Organization.builder()
                .orgCode(request.getOrgCode())
                .orgName(request.getOrgName())
                .complexId(complexId)
                .parentOrgId(parentOrgId)
                .description(request.getDescription())
                .level(level)
                .status("0")
                .build();

        Organization saved = organizationRepository.save(org);

        // Create org-building relationships
        if (request.getBuildingIds() != null && !request.getBuildingIds().isEmpty()) {
            List<OrgBuilding> orgBuildings = request.getBuildingIds().stream()
                    .map(buildingId -> OrgBuilding.builder()
                            .orgId(saved.getId())
                            .buildingId(buildingId)
                            .build())
                    .toList();
            orgBuildingRepository.saveAll(orgBuildings);
        }

        return OrganizationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public OrganizationResponse update(String id, UpdateOrganizationRequest request, String complexId) {
        Organization org = organizationRepository.findByIdAndStatusEquals(id, "0")
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // Validate org code uniqueness (only if changed)
        if (!request.getOrgCode().equals(org.getOrgCode())) {
            if (organizationRepository.existsByComplexIdAndOrgCodeAndStatusEquals(complexId, request.getOrgCode(), "0")) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
        }

        org.setOrgCode(request.getOrgCode());
        org.setOrgName(request.getOrgName());
        org.setDescription(request.getDescription());
        org.setUpdatedAt(Instant.now());

        // Handle parent organization change
        if (request.getParentOrgId() != null && !request.getParentOrgId().isEmpty()
                && !request.getParentOrgId().equals(org.getParentOrgId())) {

            Organization newParent = organizationRepository.findByIdAndStatusEquals(request.getParentOrgId(), "0")
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            int newLevel = newParent.getLevel() + 1;
            if (newLevel > 3) {
                throw new AppException(ErrorCode.MAX_ORG_LEVEL);
            }

            org.setParentOrgId(request.getParentOrgId());
            org.setLevel(newLevel);
        }

        Organization updated = organizationRepository.save(org);

        // Update org-building relationships
        if (request.getBuilding() != null && !request.getBuilding().isEmpty()) {
            orgBuildingRepository.deleteByOrgIdAndDeletedAtIsNull(id);
            List<OrgBuilding> orgBuildings = request.getBuilding().stream()
                    .map(buildingId -> OrgBuilding.builder()
                            .orgId(id)
                            .buildingId(buildingId)
                            .build())
                    .toList();
            orgBuildingRepository.saveAll(orgBuildings);
        }

        return OrganizationMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(List<String> organizationIds) {
        List<Organization> orgs = organizationRepository.findAllById(organizationIds);
        orgs.forEach(org -> {
            org.setStatus("1"); // Soft delete
            org.setDeletedAt(Instant.now());
        });
        organizationRepository.saveAll(orgs);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getTopLevel(String complexId) {
        Integer maxLevel = organizationRepository.getMaxLevel(complexId);
        return maxLevel != null ? maxLevel : 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAvailableBuildingIds(String parentOrgId, String complexId) {
        List<String> allBuildingIds = buildingRepository.findByComplexId(complexId).stream()
                .map(Building::getId)
                .toList();

        List<String> managedBuildingIds = orgBuildingRepository.findBuildingIdsByParentOrgId(parentOrgId);

        return allBuildingIds.stream()
                .filter(id -> !managedBuildingIds.contains(id))
                .toList();
    }
}
