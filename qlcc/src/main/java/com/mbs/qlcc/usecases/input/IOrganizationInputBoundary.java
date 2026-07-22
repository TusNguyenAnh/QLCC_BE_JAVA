package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Organization.*;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.usecases.response.PageResponse;

import java.util.List;


public interface IOrganizationInputBoundary {
    PageResponse<OrganizationResponse> show(String complexId, int page, int perPage);

    OrganizationResponse findById(String id);

    List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId);

    OrganizationResponse create(CreateOrganizationInpRequest request);

    OrganizationResponse update(String id, UpdateOrganizationInpRequest request);

    void delete(List<String> organizationIds);

    Integer getTopLevel(String complexId);

    List<String> getAvailableBuildingIds(String parentOrgId,String complexId);
}
