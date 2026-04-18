package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Organization.CreateOrganizationRequest;
import com.mbs.qlcc.adapters.request.Organization.UpdateOrganizationRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.OrganizationService;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.Organization.OrganizationWithoutChildResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public ApiResponse<PageResponse<OrganizationResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int perPage,
            @RequestParam String complexId) {

        // In production, extract complexId from JWT token

        return ApiResponse.<PageResponse<OrganizationResponse>>builder()
                .result(organizationService.show(complexId, page, Math.min(perPage, 50)))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrganizationResponse> findById(@PathVariable String id) {
        return ApiResponse.<OrganizationResponse>builder()
                .result(organizationService.findById(id))
                .build();
    }

    @GetMapping("/without-descendants")
    public ApiResponse<List<OrganizationWithoutChildResponse>> getAllWithoutDescendants(
            @RequestParam String parentOrgId,
            @RequestParam String complexId) {

        return ApiResponse.<List<OrganizationWithoutChildResponse>>builder()
                .result(organizationService.getAllWithoutDescendants(parentOrgId, complexId))
                .build();
    }

    @PostMapping
    public ApiResponse<OrganizationResponse> create(
            @RequestBody CreateOrganizationRequest request,
            @RequestParam String complexId) {

        return ApiResponse.<OrganizationResponse>builder()
                .result(organizationService.create(complexId, request))
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<OrganizationResponse> update(
            @PathVariable String id,
            @RequestBody UpdateOrganizationRequest request) {

        return ApiResponse.<OrganizationResponse>builder()
                .result(organizationService.update(id, request))
                .build();
    }

    @DeleteMapping
    public ApiResponse<String> delete(@RequestBody List<String> organizationIds) {
        organizationService.delete(organizationIds);

        return ApiResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }

    @GetMapping("/top-level/{complexId}")
    public ApiResponse<Integer> getTopLevel(@PathVariable String complexId) {
        return ApiResponse.<Integer>builder()
                .result(organizationService.getTopLevel(complexId))
                .build();
    }

    @GetMapping("/available-buildings")
    public ApiResponse<List<String>> getAvailableBuildingIds(
            @RequestParam String parentOrgId) {

        return ApiResponse.<List<String>>builder()
                .result(organizationService.getAvailableBuildingIds(parentOrgId))
                .build();
    }
}
