package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.adapters.imports.ResidenImportResult;
import com.mbs.qlcc.usecases.request.Resident.*;
import com.mbs.qlcc.usecases.response.Organization.OrgUserResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Resident.ResAptBdResponse;
import com.mbs.qlcc.usecases.response.Resident.ResUserResponse;
import com.mbs.qlcc.usecases.response.Resident.ResidentResponse;

import java.util.List;

public interface IResidentInputBoundary {
    ResidentResponse createResident(CreateResidentInpRequest request);

    ResidentResponse findById(String id);

    PageResponse<ResAptBdResponse> filterResident(String complexId, FilterResidentInpRequest request);

    List<ResUserResponse> findByBuildingId(List<String> buildingId, String complexId);

    List<ResUserResponse> findByOrgId(String orgId);

    List<String> importResidents(List<ImportResidentInpRequest> residents, String complexId);

    List<String> importAptResidents(List<ImportAptResidentInpRequest> request, String complexId);

    String addResidentsToOrg(UpdateResidentInOrgInpRequest request);

    void removeResidentsFromOrg(UpdateResidentInOrgInpRequest request);

    OrgUserResponse updatePositionInOrg(String residentId, String orgId, String roleId);

}
