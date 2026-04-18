package com.mbs.qlcc.usecases.output.Organization;

import com.mbs.qlcc.entities.Organization.Organization;
import com.mbs.qlcc.usecases.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Organization Data Store Gateway Interface
 * Defines contract for accessing organization data
 */
public interface IOrganizationDsGateway {
    
    /**
     * Get all root organizations for a complex (paginated)
     */
    PageResponse<Organization> show(String complexId, int page, int perPage);
    
    /**
     * Get all organizations for a complex except descendants of a specific org
     * Used when updating parent_org_id to prevent circular references
     */
    List<Organization> getAllWithoutDescendants(String parentOrgId, String complexId);
    
    /**
     * Find organization by ID
     */
    Optional<Organization> getById(String id);
    
    /**
     * Get maximum organizational level in a complex
     */
    Integer getTopLevel(String complexId);
    
    /**
     * Create a new organization
     */
    Organization store(Organization organization);
    
    /**
     * Update an existing organization
     */
    Organization update(Organization organization);
    
    /**
     * Soft delete organizations
     */
    void delete(List<String> organizationIds);
    
    /**
     * Check if root organization already exists for complex
     */
    boolean existsRootOrg(String complexId);
    
    /**
     * Check if organization code exists in complex
     */
    boolean existsOrgCode(String complexId, String orgCode);
    
    /**
     * Find organizations by status and complex
     */
    List<Organization> findByComplexIdAndStatus(String complexId, String status);
}
