package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.FinancialModel.FinancialModelRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.service.IFinancialModelService;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/financial")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinancialModelController {
    IFinancialModelService financialModelService;

    @PostMapping("")
    public ApiResponse<String> create(@RequestBody FinancialModelRequest request) {
        String complexId = getCurrentComplexId();
        return ApiResponse.<String>builder()
                .result(financialModelService.setFinancialModel(complexId, request))
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }

}
