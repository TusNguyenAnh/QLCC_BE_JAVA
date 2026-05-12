package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Building.Building;
import com.mbs.qlcc.entities.Building.IBuildingFactory;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IBuildingInputBoundary;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.request.Building.CreateBuildingInpRequest;
import com.mbs.qlcc.usecases.request.Building.UpdateBuildingInpRequest;
import com.mbs.qlcc.usecases.request.Building.UpdateRatioInpRequest;
import com.mbs.qlcc.usecases.response.Building.BuildingResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingInteractor implements IBuildingInputBoundary {

    private final IBuildingDsGateway buildingGateway;
    private final IBuildingFactory buildingFactory;

    public BuildingInteractor(IBuildingDsGateway buildingGateway, IBuildingFactory buildingFactory) {
        this.buildingGateway = buildingGateway;
        this.buildingFactory = buildingFactory;
    }

    // ========== CRUD Operations ==========

    @Override
    public BuildingResponse create(CreateBuildingInpRequest request) {
        // Check if building name already exists for this complex
        if (buildingGateway.existsByBuildingNameAndComplexId(request.getBuildingName(), request.getComplexId())) {
            throw new AppException(ErrorCode.BUILDING_NAME_EXISTS);
        }

        // Create building entity
        Building building = buildingFactory.create(
                request.getComplexId(),
                request.getBuildingName(),
                request.getFinancialRatio()
        );

        // Save to database
        Building saved = buildingGateway.save(building);

        return mapToResponse(saved);
    }

    @Override
    public BuildingResponse findById(String id) {
        Building building = buildingGateway.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));

        return mapToResponse(building);
    }

    @Override
    public List<BuildingResponse> findByComplexId(String complexId) {
        List<Building> buildings = buildingGateway.findByComplexId(complexId);
        return buildings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<BuildingResponse> findByComplexIdWithPagination(String complexId, int page, int size) {
        PageResponse<Building> buildingsPage = buildingGateway.findByComplexId(complexId, page, size);

        return new PageResponse<BuildingResponse>(
                buildingsPage.getData().stream().map(this::mapToResponse).toList(),
                buildingsPage.getPage(),
                buildingsPage.getSize(),
                buildingsPage.getTotalElements(),
                buildingsPage.getTotalPages()
        );
    }

    @Override
    public BuildingResponse update(String id, UpdateBuildingInpRequest request) {
        Building building = buildingGateway.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));

        // check duplicate name if updating

        boolean existingWithName = buildingGateway.existsByBuildingNameAndComplexId(request.getBuildingName(), building.getComplexId());
        if (existingWithName)
            throw new AppException(ErrorCode.BUILDING_NAME_EXISTS);

        // Check if updating building name - must be unique within complex
        if (request.getBuildingName() != null && !request.getBuildingName().equals(building.getBuildingName())) {
            building.setBuildingName(request.getBuildingName());
        }

        // Update financial ratio if provided
        if (request.getFinancialRatio() != null) {
            building.setFinancialRatio(request.getFinancialRatio());
        }

        building.setUpdatedAt(LocalDateTime.now());

        Building updated = buildingGateway.save(building);
        return mapToResponse(updated);
    }

    @Override
    public void delete(List<String> buildingIds) {
        if (buildingIds == null || buildingIds.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        buildingGateway.deleteByIds(buildingIds);
    }

    // ========== Financial Ratio Operation ==========

    @Override
    public void updateRatio(UpdateRatioInpRequest request) {
        // Extract building IDs
        List<String> buildingIds = request.getRatios().stream()
                .map(UpdateRatioInpRequest.RatioItem::getId)
                .distinct()
                .collect(Collectors.toList());

        // Step 1: Validate all building IDs exist and belong to complex
        List<Building> validatedBuildings = buildingGateway.validateBuildingIds(
                buildingIds,
                request.getComplexId()
        );

        if (validatedBuildings.size() != buildingIds.size()) {
            throw new AppException(ErrorCode.BUILDING_NOT_FOUND);
        }

        // Step 2: Validate that total financial ratio = 100%
        Float totalRatio = request.getRatios().stream()
                .map(UpdateRatioInpRequest.RatioItem::getFinancialRatio)
                .reduce(0f, Float::sum);

        if (Math.abs(totalRatio - 100f) > 0.001) {  // Allow small floating point errors
            throw new AppException(ErrorCode.FINANCIAL_TOTAL_RATIO_NOT_VALID);
        }

        // Step 3: Update financial ratios
        for (UpdateRatioInpRequest.RatioItem item : request.getRatios()) {
            buildingGateway.updateFinancialRatio(item.getId(), item.getFinancialRatio());
        }
    }

    private BuildingResponse mapToResponse(Building building) {
        if (building == null) return null;

        return new BuildingResponse(
                building.getId(),
                building.getComplexId(),
                building.getBuildingName(),
                building.getStatus(),
                building.getFinancialRatio(),
                building.getCreatedAt(),
                building.getUpdatedAt());

    }
}
