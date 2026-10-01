package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Resident.CreateResidentRequest;
import com.mbs.qlcc.dto.request.Resident.FilterResidentRequest;
import com.mbs.qlcc.dto.request.Resident.UpdatePositionRequest;
import com.mbs.qlcc.dto.request.Resident.UpdateResInOrgRequest;
import com.mbs.qlcc.dto.response.Organization.OrgUserResponse;
import com.mbs.qlcc.dto.response.Resident.ResAptBdResponse;
import com.mbs.qlcc.dto.response.Resident.ResUserResponse;
import com.mbs.qlcc.dto.response.Resident.ResidentResponse;

import com.mbs.qlcc.dto.request.Resident.ImportAptResidentRequest;

import java.util.List;

public interface IResidentService {
    ResidentResponse createResident(String complexId, CreateResidentRequest request);
    ResidentResponse findById(String id);
    List<ResAptBdResponse> filterResident(String complexId, FilterResidentRequest request);
    List<ResUserResponse> findByBuildingId(List<String> buildingIds, String orgId);
    List<ResUserResponse> findByOrgId(String orgId);
    String addResidentsToOrg(String orgId, UpdateResInOrgRequest request);
    void removeResidentsFromOrg(String orgId, UpdateResInOrgRequest request);
    OrgUserResponse updatePosition(UpdatePositionRequest request);
    String importResidents(List<CreateResidentRequest> residents, String complexId);
    String importAptResidents(List<ImportAptResidentRequest> requests, String complexId);
}
