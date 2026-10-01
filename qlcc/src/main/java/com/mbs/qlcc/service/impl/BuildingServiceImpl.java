package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.dto.request.Building.CreateBuildingRequest;
import com.mbs.qlcc.dto.request.Building.UpdateBuildingRequest;
import com.mbs.qlcc.dto.request.Building.UpdateRatioRequest;
import com.mbs.qlcc.dto.response.Building.BuildingResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Building.BuildingMapper;
import com.mbs.qlcc.repository.Building.IBuildingRepository;
import com.mbs.qlcc.service.IBuildingService;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingServiceImpl implements IBuildingService {

    IBuildingRepository buildingRepository;

    @Override
    @Transactional
    public BuildingResponse create(CreateBuildingRequest request) {
        if (buildingRepository.existsByBuildingNameAndComplexIdAndDeletedAtIsNull(request.getBuildingName(), request.getComplexId())) {
            throw new AppException(ErrorCode.BUILDING_NAME_EXISTS);
        }

        Building building = BuildingMapper.toEntity(request, request.getComplexId());
        Building saved = buildingRepository.save(building);

        return BuildingMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BuildingResponse findById(String id) {
        Building building = buildingRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));

        return BuildingMapper.toResponse(building);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuildingResponse> findByComplexId(String complexId) {
        List<Building> buildings = buildingRepository.findByComplexId(complexId);
        return buildings.stream()
                .map(BuildingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BuildingResponse> findByComplexIdWithPagination(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Building> buildingsPage = buildingRepository.findByComplexIdAndDeletedAtIsNull(complexId, pageable);

        return new PageResponse<>(
                buildingsPage.getContent().stream().map(BuildingMapper::toResponse).toList(),
                buildingsPage.getNumber(),
                buildingsPage.getSize(),
                buildingsPage.getTotalElements(),
                buildingsPage.getTotalPages()
        );
    }

    @Override
    @Transactional
    public BuildingResponse update(String id, UpdateBuildingRequest request) {
        Building building = buildingRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));

        boolean exists = buildingRepository.existsByBuildingNameAndComplexIdAndDeletedAtIsNull(request.getBuildingName(), building.getComplexId());
        if (exists) {
            throw new AppException(ErrorCode.BUILDING_NAME_EXISTS);
        }

        if (request.getBuildingName() != null && !request.getBuildingName().equals(building.getBuildingName())) {
            building.setBuildingName(request.getBuildingName());
        }
        if (request.getFinancialRatio() != null) {
            building.setFinancialRatio(request.getFinancialRatio());
        }
        building.setUpdatedAt(LocalDateTime.now());

        Building updated = buildingRepository.save(building);
        return BuildingMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(List<String> buildingIds) {
        if (buildingIds == null || buildingIds.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<Building> buildings = buildingRepository.findAllById(buildingIds);
        if (buildings.isEmpty()) {
            throw new AppException(ErrorCode.BUILDING_NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();
        for (Building building : buildings) {
            building.setDeletedAt(now);
            building.setStatus(1);
        }

        buildingRepository.saveAll(buildings);
    }

    @Override
    @Transactional
    public void updateRatio(UpdateRatioRequest request) {
        if (request == null || request.getRatios() == null || request.getRatios().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<String> buildingIds = request.getRatios().stream()
                .map(UpdateRatioRequest.RatioItem::getId)
                .distinct()
                .toList();

        // Step 1: Validate all building IDs exist and belong to the complex
        List<Building> validatedBuildings = buildingRepository.findAllByIdInAndComplexIdAndDeletedAtIsNull(
                buildingIds,
                request.getComplexId()
        );

        if (validatedBuildings.size() != buildingIds.size()) {
            throw new AppException(ErrorCode.BUILDING_NOT_FOUND);
        }

        // Step 2: Validate total ratio = 100%
        Float totalRatio = request.getRatios().stream()
                .map(UpdateRatioRequest.RatioItem::getFinancialRatio)
                .reduce(0f, Float::sum);

        if (Math.abs(totalRatio - 100f) > 0.001) {
            throw new AppException(ErrorCode.FINANCIAL_TOTAL_RATIO_NOT_VALID);
        }

        // Step 3: Batch update ratios
        Map<String, Float> ratioMap = request.getRatios().stream()
                .collect(Collectors.toMap(
                        UpdateRatioRequest.RatioItem::getId,
                        UpdateRatioRequest.RatioItem::getFinancialRatio,
                        (existing, replacement) -> replacement
                ));

        for (Building building : validatedBuildings) {
            Float ratio = ratioMap.get(building.getId());
            if (ratio != null) {
                building.setFinancialRatio(ratio);
                building.setUpdatedAt(LocalDateTime.now());
            }
        }

        buildingRepository.saveAll(validatedBuildings);
    }
}
