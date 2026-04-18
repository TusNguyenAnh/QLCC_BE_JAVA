package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Organization.CreateOrganizationRequest;
import com.mbs.qlcc.adapters.request.Organization.UpdateOrganizationRequest;
import com.mbs.qlcc.usecases.input.IOrganizationInputBoundary;
import com.mbs.qlcc.usecases.request.Organization.CreateOrganizationInpRequest;
import com.mbs.qlcc.usecases.request.Organization.UpdateOrganizationInpRequest;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Organization Service - Adapter Layer
 * Bridges Controller and Usecase layers
 * Converts adapter DTOs to usecase DTOs
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationService {

    IOrganizationInputBoundary useCase;

    /**
     * Get all root organizations for a complex (paginated)
     */
    public PageResponse<OrganizationResponse> show(String complexId, int page, int perPage) {
        return useCase.show(complexId, page, perPage);
    }

    /**
     * Find organization by ID
     */
    public OrganizationResponse findById(String id) {
        return useCase.findById(id);
    }

    /**
     * Get organizations excluding descendants of a specific organization
     */
    public List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId) {
        return useCase.getAllWithoutDescendants(parentOrgId, complexId);
    }

    /**
     * Create a new organization
     */
    @Transactional
    public OrganizationResponse create(String complexId, CreateOrganizationRequest request) {
        CreateOrganizationInpRequest inpRequest = new CreateOrganizationInpRequest(
                request.getOrgCode(),
                request.getOrgName(),
                complexId,
                request.getParentOrgId(),
                request.getDescription(),
                request.getBuildingIds()
        );
        return useCase.create(inpRequest);
    }

    /**
     * Update an organization
     */
    @Transactional
    public OrganizationResponse update(String id, UpdateOrganizationRequest request) {
        UpdateOrganizationInpRequest inpRequest = new UpdateOrganizationInpRequest(
                request.getComplexId(),
                request.getOrgCode(),
                request.getOrgName(),
                request.getDescription(),
                request.getParentOrgId(),
                request.getBuildingIds()
        );
        return useCase.update(id, inpRequest);
    }

    /**
     * Delete organizations (soft delete)
     */
    @Transactional
    public void delete(List<String> organizationIds) {
        useCase.delete(organizationIds);
    }

    /**
     * Get maximum organizational level in a complex
     */
    public Integer getTopLevel(String complexId) {
        return useCase.getTopLevel(complexId);
    }

    /**
     * Get available building IDs for a parent organization
     */
    public List<String> getAvailableBuildingIds(String parentOrgId) {
        return useCase.getAvailableBuildingIds(parentOrgId);
    }
}
