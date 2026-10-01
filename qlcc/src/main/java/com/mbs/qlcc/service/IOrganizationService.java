package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Organization.CreateOrganizationRequest;
import com.mbs.qlcc.dto.request.Organization.UpdateOrganizationRequest;
import com.mbs.qlcc.dto.response.Organization.OrganizationResponse;
import com.mbs.qlcc.dto.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.dto.response.PageResponse;

import java.util.List;

public interface IOrganizationService {
    PageResponse<OrganizationResponse> show(String complexId, int page, int perPage);
    OrganizationResponse findById(String id);
    List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId);
    OrganizationResponse create(String complexId, CreateOrganizationRequest request);
    OrganizationResponse update(String id, UpdateOrganizationRequest request, String complexId);
    void delete(List<String> organizationIds);
    Integer getTopLevel(String complexId);
    List<String> getAvailableBuildingIds(String parentOrgId, String complexId);
}
