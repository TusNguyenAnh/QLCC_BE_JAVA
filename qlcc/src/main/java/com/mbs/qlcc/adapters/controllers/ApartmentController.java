package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.imports.ApartmentImportResult;
import com.mbs.qlcc.adapters.services.ApartmentImportService;
import com.mbs.qlcc.adapters.request.Apartment.CreateApartmentRequest;
import com.mbs.qlcc.adapters.request.Apartment.FilterApartmentRequest;
import com.mbs.qlcc.adapters.request.Apartment.UpdateApartmentRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.ApartmentService;
import com.mbs.qlcc.usecases.response.Apartment.ApartmentResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ApartmentImportService importService;

    @PostMapping("")
    public ApiResponse<ApartmentResponse> create(
            @RequestBody CreateApartmentRequest request) {


        String complexId = getCurrentComplexId(); // TODO: Get from security context

        ApartmentResponse response = apartmentService.create(request, complexId);
        return ApiResponse.<ApartmentResponse>builder()
                .result(response)
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<ApartmentResponse> findById(@PathVariable String id) {
        ApartmentResponse response = apartmentService.findById(id);
        return ApiResponse.<ApartmentResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/building/{buildingId}")
    public ApiResponse<PageResponse<ApartmentResponse>> findByBuildingId(
            @PathVariable String buildingId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "50") int pageSize) {

        PageResponse<ApartmentResponse> response = apartmentService.findByBuildingId(buildingId, pageNumber, pageSize);
        return ApiResponse.<PageResponse<ApartmentResponse>>builder()
                .result(response)
                .build();
    }

    @PostMapping("/filter/{status}")
    public ApiResponse<PageResponse<ApartmentResponse>> filterByStatus(
            @PathVariable int status,
            @RequestBody FilterApartmentRequest request) {

        PageResponse<ApartmentResponse> response = apartmentService.filterByStatus(status, request);
        return ApiResponse.<PageResponse<ApartmentResponse>>builder()
                .result(response)
                .build();
    }

    @GetMapping("/complex/{complexId}")
    public ApiResponse<PageResponse<ApartmentResponse>> findByComplexId(
            @PathVariable String complexId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "50") int pageSize) {

        PageResponse<ApartmentResponse> response = apartmentService.findByComplexId(complexId, pageNumber, pageSize);
        return ApiResponse.<PageResponse<ApartmentResponse>>builder()
                .result(response)
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<ApartmentResponse> update(
            @PathVariable String id,
            @RequestBody UpdateApartmentRequest request) {

        ApartmentResponse response = apartmentService.update(id, request);
        return ApiResponse.<ApartmentResponse>builder()
                .result(response)
                .build();
    }


    @PostMapping(value = "/import-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApartmentImportResult> importFromExcel(
            @RequestParam("file") MultipartFile file) {

        String complexId = getCurrentComplexId();
        ApartmentImportResult result = importService.importFromExcel(file, complexId);

        return ApiResponse.<ApartmentImportResult>builder()
                .result(result)
                .build();
    }

    private String getCurrentComplexId() {
        // This should be retrieved from JWT token
        // For now, returning placeholder
        return "complex-id-from-jwt";
    }
}
