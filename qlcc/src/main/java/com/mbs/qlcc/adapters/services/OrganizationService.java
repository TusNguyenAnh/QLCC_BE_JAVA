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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationService {

    IOrganizationInputBoundary useCase;

    public PageResponse<OrganizationResponse> show(String complexId, int page, int perPage) {
        return useCase.show(complexId, page, perPage);
    }

    public OrganizationResponse findById(String id) {
        return useCase.findById(id);
    }

    public List<OrganizationWithoutChildResponse> getAllWithoutDescendants(String parentOrgId, String complexId) {
        return useCase.getAllWithoutDescendants(parentOrgId, complexId);
    }

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

    @Transactional
    public OrganizationResponse update(String id, UpdateOrganizationRequest request, String complexId) {
        UpdateOrganizationInpRequest inpRequest = new UpdateOrganizationInpRequest(
                complexId,
                request.getOrgCode(),
                request.getOrgName(),
                request.getDescription(),
                request.getParentOrgId(),
                request.getBuilding()
        );
        return useCase.update(id, inpRequest);
    }

    @Transactional
    public void delete(List<String> organizationIds) {
        useCase.delete(organizationIds);
    }

    public Integer getTopLevel(String complexId) {
        return useCase.getTopLevel(complexId);
    }

    public List<String> getAvailableBuildingIds(String parentOrgId, String complexId) {
        return useCase.getAvailableBuildingIds(parentOrgId, complexId);
    }
}
