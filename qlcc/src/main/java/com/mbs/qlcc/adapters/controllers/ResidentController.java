package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.imports.ApartmentImportResult;
import com.mbs.qlcc.adapters.imports.AptResImportResult;
import com.mbs.qlcc.adapters.imports.ResidenImportResult;
import com.mbs.qlcc.adapters.request.Resident.*;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.AptResImportService;
import com.mbs.qlcc.adapters.services.ResidentImportService;
import com.mbs.qlcc.adapters.services.ResidentService;
import com.mbs.qlcc.usecases.response.Organization.OrgUserResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Resident.ResAptBdResponse;
import com.mbs.qlcc.usecases.response.Resident.ResUserResponse;
import com.mbs.qlcc.usecases.response.Resident.ResidentResponse;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resident")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResidentController {
    ResidentService residentService;
    ResidentImportService residentImportService;
    AptResImportService aptResImportService;

    @PostMapping("")
    public ApiResponse<ResidentResponse> createResident(
            @RequestBody CreateResidentRequest request) {
        String complexId = getCurrentComplexId();

        return ApiResponse.<ResidentResponse>builder()
                .result(residentService.createResident(complexId, request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ResidentResponse> findById(@PathVariable String id) {
        return ApiResponse.<ResidentResponse>builder()
                .result(residentService.findById(id))
                .build();
    }


    @PostMapping("/filter")
    public ApiResponse<List<ResAptBdResponse>> filterResident(@RequestBody FilterResidentRequest request) {
        String complexId = getCurrentComplexId();

        return ApiResponse.<List<ResAptBdResponse>>builder()
                .result(residentService.filterResident(complexId, request))
                .build();
    }


    @PostMapping("/findByBuildingId/{orgId}")
    public ApiResponse<List<ResUserResponse>> findByBuildingId(@RequestBody List<String> buildingIds, @PathVariable String orgId) {
        return ApiResponse.<List<ResUserResponse>>builder()
                .result(residentService.findByBuildingId(buildingIds, orgId))
                .build();
    }


    @GetMapping("/findByOrgId/{orgId}")
    public ApiResponse<List<ResUserResponse>> findByOrgId(@PathVariable String orgId) {
        return ApiResponse.<List<ResUserResponse>>builder()
                .result(residentService.findByOrgId(orgId))
                .build();
    }

    @PostMapping("/addResInOrg/{orgId}")
    public ApiResponse<String> addResidentsToOrg(@PathVariable String orgId, @RequestBody UpdateResInOrgRequest request) {
        return ApiResponse.<String>builder()
                .result(residentService.addResidentsToOrg(orgId, request))
                .build();
    }

    @PostMapping("/removeResInOrg/{orgId}")
    public ApiResponse<String> removeResidentsFromOrg(@PathVariable String orgId, @RequestBody UpdateResInOrgRequest request) {
        residentService.removeResidentsFromOrg(orgId, request);

        return ApiResponse.<String>builder()
                .result("Loại bỏ thành công")
                .build();
    }


    @PostMapping("/updatePosition")
    public ApiResponse<OrgUserResponse> updatePosition(@RequestBody UpdatePositionRequest request) {
        return ApiResponse.<OrgUserResponse>builder()
                .result(residentService.updatePosition(request))
                .build();
    }


    @PostMapping(value = "/import-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResidenImportResult> importResidents(@RequestParam("files") MultipartFile file) {
        String complexId = getCurrentComplexId();
        ResidenImportResult result = residentImportService.importFromExcel(file, complexId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping(value = "/import-excelAptRes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AptResImportResult> importAptResidents(@RequestParam("files") MultipartFile file) {
        String complexId = getCurrentComplexId();
        AptResImportResult result = aptResImportService.importFromExcel(file, complexId);
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
