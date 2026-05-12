package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Resident.CreateResidentRequest;
import com.mbs.qlcc.adapters.request.Resident.FilterResidentRequest;
import com.mbs.qlcc.adapters.request.Resident.UpdatePositionRequest;
import com.mbs.qlcc.adapters.request.Resident.UpdateResInOrgRequest;
import com.mbs.qlcc.usecases.input.IResidentInputBoundary;
import com.mbs.qlcc.usecases.request.Resident.CreateResidentInpRequest;
import com.mbs.qlcc.usecases.request.Resident.FilterResidentInpRequest;
import com.mbs.qlcc.usecases.request.Resident.UpdateResidentInOrgInpRequest;
import com.mbs.qlcc.usecases.response.Organization.OrgUserResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Resident.ResAptBdResponse;
import com.mbs.qlcc.usecases.response.Resident.ResUserResponse;
import com.mbs.qlcc.usecases.response.Resident.ResidentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentService {
    private final IResidentInputBoundary residentUseCase;

    @Transactional
    public ResidentResponse createResident(String complexId, CreateResidentRequest request) {
        CreateResidentInpRequest inpRequest = new CreateResidentInpRequest(
                complexId,
                request.getFullname(),
                request.getGender(),
                request.getEmail(),
                request.getBirthday(),
                request.getRelationship(),
                request.getPhoneNumber(),
                request.getCccd()
        );

        return residentUseCase.createResident(inpRequest);
    }

    public ResidentResponse findById(String id) {
        return residentUseCase.findById(id);
    }

    public PageResponse<ResAptBdResponse> filterResident(String complexId, FilterResidentRequest request) {
        FilterResidentInpRequest inpRequest = new FilterResidentInpRequest(
                request.getFloor(),
                request.getBuildingId(),
                request.getAptNumber(),
                request.getRelationship(),
                request.getOrder(),
                request.getPageNumber(),
                request.getPageSize()
        );

        return residentUseCase.filterResident(complexId, inpRequest);
    }


    public List<ResUserResponse> findByBuildingId(List<String> buildingIds, String complexId) {
        return residentUseCase.findByBuildingId(buildingIds, complexId);
    }

    public List<ResUserResponse> findByOrgId(String orgId) {
        return residentUseCase.findByOrgId(orgId);
    }

    @Transactional
    public String addResidentsToOrg(String orgId, UpdateResInOrgRequest request) {
        UpdateResidentInOrgInpRequest inpRequest = new UpdateResidentInOrgInpRequest(orgId, request.getUserIds());
        return residentUseCase.addResidentsToOrg(inpRequest);
    }

    @Transactional
    public void removeResidentsFromOrg(String orgId, UpdateResInOrgRequest request) {
        UpdateResidentInOrgInpRequest inpRequest = new UpdateResidentInOrgInpRequest(orgId, request.getUserIds());
        residentUseCase.removeResidentsFromOrg(inpRequest);
    }

    @Transactional
    public OrgUserResponse updatePosition(UpdatePositionRequest request) {
        return residentUseCase.updatePositionInOrg(
                request.getUserId(),
                request.getOrgId(),
                request.getRoleId()
        );
    }
}
