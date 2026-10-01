package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Building.CreateBuildingRequest;
import com.mbs.qlcc.dto.request.Building.UpdateBuildingRequest;
import com.mbs.qlcc.dto.request.Building.UpdateRatioRequest;
import com.mbs.qlcc.dto.response.Building.BuildingResponse;
import com.mbs.qlcc.dto.response.PageResponse;

import java.util.List;

public interface IBuildingService {

    BuildingResponse create(CreateBuildingRequest request);

    BuildingResponse findById(String id);

    List<BuildingResponse> findByComplexId(String complexId);

    PageResponse<BuildingResponse> findByComplexIdWithPagination(String complexId, int page, int size);

    BuildingResponse update(String id, UpdateBuildingRequest request);

    void delete(List<String> buildingIds);

    void updateRatio(UpdateRatioRequest request);
}
