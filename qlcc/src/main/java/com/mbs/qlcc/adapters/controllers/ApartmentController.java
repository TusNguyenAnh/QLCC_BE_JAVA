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
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/apartments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentController {
    ApartmentService apartmentService;
    ApartmentImportService importService;

    @PostMapping("")
    public ApiResponse<ApartmentResponse> create(
            @RequestBody CreateApartmentRequest request) {
        String complexId = getCurrentComplexId();

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
    public ResponseEntity<ApartmentImportResult> importFromExcel(
            @RequestParam("files") MultipartFile file) {

        String complexId = getCurrentComplexId();
        ApartmentImportResult result = importService.importFromExcel(file, complexId);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }
}
