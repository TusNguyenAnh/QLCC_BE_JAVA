package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Complex.ApproveRejectComplexRequest;
import com.mbs.qlcc.adapters.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.adapters.request.Complex.FilterComplexRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.ComplexService;
import com.mbs.qlcc.adapters.services.MediaFileService;
import com.mbs.qlcc.usecases.response.Complex.ComplexResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/complexes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComplexController {

    ComplexService complexService;

    @PostMapping("")
    public ApiResponse<ComplexResponse> create(@ModelAttribute CreateComplexRequest request) throws IOException {
        return ApiResponse.<ComplexResponse>builder()
                .result(complexService.create(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ComplexResponse> findById(@PathVariable String id) {
        return ApiResponse.<ComplexResponse>builder()
                .result(complexService.findById(id))
                .build();
    }

    @PostMapping("/filter/{status}")
    public ApiResponse<PageResponse<ComplexResponse>> filterByStatus(
            @PathVariable int status,
            @RequestBody FilterComplexRequest request) {
        return ApiResponse.<PageResponse<ComplexResponse>>builder()
                .result(complexService.filterByStatus(status, request))
                .build();
    }

    @PostMapping("/approve")
    public ApiResponse<List<ComplexResponse>> approveComplex(
            @RequestBody ApproveRejectComplexRequest request) {
        return ApiResponse.<List<ComplexResponse>>builder()
                .result(complexService.approveComplex(request))
                .build();
    }


    @PostMapping("/reject")
    public ApiResponse<Void> rejectComplex(
            @RequestBody ApproveRejectComplexRequest request) {
        complexService.rejectComplex(request);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Complexes rejected successfully")
                .build();
    }
}

