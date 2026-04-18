package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Organization.*;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.usecases.response.PageResponse;

import java.util.List;

/**
 * Organization Input Boundary Interface
 * Defines use case operations for Organization
 */
public interface IOrganizationInputBoundary {
    
    /**
     * Get all root organizations for a complex (paginated)
     */
    PageResponse<OrganizationResponse> show(String complexId, int page, int perPage);
    
    /**
     * Find organization by ID with hierarchical structure
     */
    OrganizationResponse findById(String id);
    
    /**
     * Get all organizations excluding descendants of a specific organization
     * Used when updating parent_org_id
     */
    List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId);
    
    /**
     * Create a new organization
     */
    OrganizationResponse create(CreateOrganizationInpRequest request);
    
    /**
     * Update an existing organization
     */
    OrganizationResponse update(String id, UpdateOrganizationInpRequest request);
    
    /**
     * Delete organizations (soft delete)
     */
    void delete(List<String> organizationIds);
    
    /**
     * Get maximum organizational level in a complex
     */
    Integer getTopLevel(String complexId);
    
    /**
     * Get available building IDs that can be assigned to a parent organization's children
     */
    List<String> getAvailableBuildingIds(String parentOrgId);
}
