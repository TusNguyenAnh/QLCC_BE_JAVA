package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Building.Building;
import com.mbs.qlcc.entities.Organization.Organization;
import com.mbs.qlcc.entities.Organization.IOrganizationFactory;
import com.mbs.qlcc.entities.Organization.OrgBuilding;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IOrganizationInputBoundary;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrganizationDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgBuildingDsGateway;
import com.mbs.qlcc.usecases.request.Organization.CreateOrganizationInpRequest;
import com.mbs.qlcc.usecases.request.Organization.UpdateOrganizationInpRequest;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.ErrorCode;


import java.util.*;
import java.util.stream.Collectors;

public class OrganizationInteractor implements IOrganizationInputBoundary {
    private final IOrganizationDsGateway organizationGateway;
    private final IOrgBuildingDsGateway orgBuildingGateway;
    private final IBuildingDsGateway buildingGateway;
    private final IOrganizationFactory organizationFactory;

    public OrganizationInteractor(IOrganizationDsGateway organizationGateway, IOrgBuildingDsGateway orgBuildingGateway, IBuildingDsGateway buildingGateway, IOrganizationFactory organizationFactory) {
        this.organizationGateway = organizationGateway;
        this.orgBuildingGateway = orgBuildingGateway;
        this.buildingGateway = buildingGateway;
        this.organizationFactory = organizationFactory;
    }

    @Override
    public PageResponse<OrganizationResponse> show(String complexId, int page, int perPage) {
        PageResponse<Organization> result = organizationGateway.show(complexId, page, perPage);
        return new PageResponse<OrganizationResponse>(
                result.getData().stream().map(this::mapToResponse).toList(),
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public OrganizationResponse findById(String id) {
        return organizationGateway.getById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    @Override
    public List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId) {
        List<Organization> orgs = organizationGateway.getAllWithoutDescendants(parentOrgId, complexId);
        return orgs.stream()
                .map(this::mapToResponseSimple)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationResponse create(CreateOrganizationInpRequest request) {
        int level = 1;

        if (request.getParentOrgId() != null && !request.getParentOrgId().isEmpty()) {
            // Check if parent exists
            Organization parent = organizationGateway.getById(request.getParentOrgId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            level = parent.getLevel() + 1;

            // Check max level constraint (max 3)
            if (level > 3) {
                throw new AppException(ErrorCode.MAX_ORG_LEVEL);
            }
        } else {
            // Root organization
            if (organizationGateway.existsRootOrg(request.getComplexId())) {
                throw new AppException(ErrorCode.PARENT_ORG_EXISTED);
            }
        }

        // Check if org_code already exists
        if (organizationGateway.existsOrgCode(request.getComplexId(), request.getOrgCode())) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // Create organization
        Organization org = organizationFactory.create(
                request.getOrgCode(),
                request.getOrgName(),
                request.getComplexId(),
                request.getParentOrgId(),
                request.getDescription(),
                level
        );

        Organization saved = organizationGateway.store(org);

        // Create org-building relationships
        if (request.getBuildingIds() != null && !request.getBuildingIds().isEmpty()) {
            List<OrgBuilding> orgBuildings = request.getBuildingIds().stream()
                    .map(buildingId -> new OrgBuilding(saved.getId(), buildingId))
                    .collect(Collectors.toList());
            orgBuildingGateway.store(orgBuildings);
        }

        return mapToResponse(saved);
    }

    @Override
    public OrganizationResponse update(String id, UpdateOrganizationInpRequest request) {
        Organization org = new Organization();
        // Update basic fields
        if (organizationGateway.existsOrgCode(request.getComplexId(), request.getOrgCode())) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        org.setId(id);
        org.setOrgCode(request.getOrgCode());
        org.setOrgName(request.getOrgName());
        org.setDescription(request.getDescription());

        // Handle parent organization change
        if (request.getParentOrgId() != null && !request.getParentOrgId().isEmpty()) {
            if (!request.getParentOrgId().equals(org.getParentOrgId())) {
                // Check if new parent exists
                Organization newParent = organizationGateway.getById(request.getParentOrgId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

                int newLevel = newParent.getLevel() + 1;

                if (newLevel > 3) {
                    throw new AppException(ErrorCode.MAX_ORG_LEVEL);
                }

                org.setParentOrgId(request.getParentOrgId());
                org.setLevel(newLevel);
            }
        }

        // Save updated organization
        Organization updated = organizationGateway.update(org);


        // Create new org-building relationships
        if (request.getBuildingIds() != null && !request.getBuildingIds().isEmpty()) {
            // Delete old org-building relationships
            orgBuildingGateway.deleteByOrgId(id);
            List<OrgBuilding> orgBuildings = request.getBuildingIds().stream()
                    .map(buildingId -> new OrgBuilding(id, buildingId))
                    .collect(Collectors.toList());
            orgBuildingGateway.store(orgBuildings);
        }

        return mapToResponse(updated);
    }

    @Override
    public void delete(List<String> organizationIds) {
        organizationGateway.delete(organizationIds);
    }

    @Override
    public Integer getTopLevel(String complexId) {
        return organizationGateway.getTopLevel(complexId);
    }

    // lay ra cac building ma trong cung 1 cap to chuc chua quan ly toa nha do de them moi/ thay doi su quan ly toa nha
    @Override
    public List<String> getAvailableBuildingIds(String parentOrgId, String complexId) {
        List<String> buildingIds = buildingGateway.findByComplexId(complexId).stream()
                .map(Building::getId)
                .toList();

        List<String> buildingIdsManaged = orgBuildingGateway.getBuildingIdsByParentOrgId(parentOrgId);

        return buildingIds.stream()
                .filter(id -> !buildingIdsManaged.contains(id))
                .toList();
    }

    private OrganizationResponse mapToResponse(Organization org) {
        OrganizationResponse response = new OrganizationResponse(org.getId(), org.getOrgCode(), org.getOrgName(), org.getComplexId(), org.getParentOrgId(),
                org.getDescription(), org.getStatus(), org.getLevel());

        response.setBuildingIds(org.getOrgBuildings());

        if (org.getChild() != null) {
            response.setChildren(
                    org.getChild().stream()
                            .map(this::mapToResponse) // 👈 recursion
                            .toList()
            );
        } else {
            response.setChildren(List.of());
        }

        return response;
    }

    private OrganizationWithoutChildResponse mapToResponseSimple(Organization org) {
        return new OrganizationWithoutChildResponse(org.getId(), org.getOrgCode(), org.getOrgName());
    }
}
