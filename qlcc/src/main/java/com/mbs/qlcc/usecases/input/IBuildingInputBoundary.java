package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Building.CreateBuildingInpRequest;
import com.mbs.qlcc.usecases.request.Building.UpdateBuildingInpRequest;
import com.mbs.qlcc.usecases.request.Building.UpdateRatioInpRequest;
import com.mbs.qlcc.usecases.response.Building.BuildingResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBuildingInputBoundary {

    // CRUD Operations
    BuildingResponse create(CreateBuildingInpRequest request);

    BuildingResponse findById(String id);

    List<BuildingResponse> findByComplexId(String complexId);

    PageResponse<BuildingResponse> findByComplexIdWithPagination(String complexId, int page, int size);

    BuildingResponse update(String id, UpdateBuildingInpRequest request);

    void delete(List<String> buildingIds);

    // Financial Ratio Operation
    void updateRatio(UpdateRatioInpRequest request);
}
