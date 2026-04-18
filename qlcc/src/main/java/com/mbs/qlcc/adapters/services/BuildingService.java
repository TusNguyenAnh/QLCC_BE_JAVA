package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Building.CreateBuildingRequest;
import com.mbs.qlcc.adapters.request.Building.UpdateBuildingRequest;
import com.mbs.qlcc.adapters.request.Building.UpdateRatioRequest;
import com.mbs.qlcc.usecases.input.IBuildingInputBoundary;
import com.mbs.qlcc.usecases.request.Building.CreateBuildingInpRequest;
import com.mbs.qlcc.usecases.request.Building.UpdateBuildingInpRequest;
import com.mbs.qlcc.usecases.request.Building.UpdateRatioInpRequest;
import com.mbs.qlcc.usecases.response.Building.BuildingResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingService {

    IBuildingInputBoundary buildingInteractor;

    public BuildingResponse create(CreateBuildingRequest request) {
        CreateBuildingInpRequest inpRequest = new CreateBuildingInpRequest(
                request.getComplexId(),
                request.getBuildingName(),
                request.getFinancialRatio()
        );

        return buildingInteractor.create(inpRequest);
    }

    public BuildingResponse findById(String id) {
        return buildingInteractor.findById(id);
    }

    public List<BuildingResponse> findByComplexId(String complexId) {
        return buildingInteractor.findByComplexId(complexId);
    }

    public PageResponse<BuildingResponse> findByComplexIdWithPagination(String complexId, int page, int size) {
        return buildingInteractor.findByComplexIdWithPagination(complexId, page, size);
    }

    public BuildingResponse update(String id, UpdateBuildingRequest request) {
        UpdateBuildingInpRequest inpRequest = new UpdateBuildingInpRequest(
                request.getBuildingName(),
                request.getFinancialRatio()
        );

        return buildingInteractor.update(id, inpRequest);
    }

    public void delete(List<String> buildingIds) {
        buildingInteractor.delete(buildingIds);
    }


    public void updateRatio(UpdateRatioRequest request) {
        List<UpdateRatioInpRequest.RatioItem> inpRatios = request.getRatios().stream()
                .map(item -> new UpdateRatioInpRequest.RatioItem(item.getId(), item.getFinancialRatio()))
                .collect(Collectors.toList());

        UpdateRatioInpRequest inpRequest = new UpdateRatioInpRequest(
                request.getComplexId(),
                inpRatios
        );

        buildingInteractor.updateRatio(inpRequest);
    }
}
