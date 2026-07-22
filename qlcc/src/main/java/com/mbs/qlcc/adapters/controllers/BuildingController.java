package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Building.CreateBuildingRequest;
import com.mbs.qlcc.adapters.request.Building.UpdateBuildingRequest;
import com.mbs.qlcc.adapters.request.Building.UpdateRatioRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.BuildingService;
import com.mbs.qlcc.usecases.response.Building.BuildingResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/building")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingController {
    BuildingService buildingService;

    @PostMapping
    public ApiResponse<BuildingResponse> create(
            @RequestBody CreateBuildingRequest request
    ) {
        return ApiResponse.<BuildingResponse>builder()
                .result(buildingService.create(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BuildingResponse> findById(
            @PathVariable String id
    ) {
        return ApiResponse.<BuildingResponse>builder()
                .result(buildingService.findById(id))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<BuildingResponse>> findByComplexId(
    ) {
        String currentComplexId = getCurrentComplexId();
        return ApiResponse.<List<BuildingResponse>>builder()
                .result(buildingService.findByComplexId(currentComplexId))
                .build();
    }

    @GetMapping("/complex/{complexId}/paginated")
    public ApiResponse<PageResponse<BuildingResponse>> findByComplexIdWithPagination(
            @PathVariable String complexId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ApiResponse.<PageResponse<BuildingResponse>>builder()
                .result(buildingService.findByComplexIdWithPagination(complexId, page, size))
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<BuildingResponse> update(
            @PathVariable String id,
            @RequestBody UpdateBuildingRequest request
    ) {

        return ApiResponse.<BuildingResponse>builder()
                .result(buildingService.update(id, request))
                .build();
    }

    @PostMapping("/delete")
    public ApiResponse<String> delete(
            @RequestBody List<String> buildingIds
    ) {
        buildingService.delete(buildingIds);

        return ApiResponse.<String>builder()
                .result("Buildings deleted successfully!")
                .build();
    }

    @PostMapping("/ratio/update")
    public ApiResponse<String> updateRatio(
            @RequestBody UpdateRatioRequest request
    ) {
        buildingService.updateRatio(request);

        return ApiResponse.<String>builder()
                .result("Update ratio successfully!")
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }
}
