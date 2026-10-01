package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Workflow.WorkflowRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.service.IWorkflowService;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Workflow.WorkflowResponse;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workflow")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkflowController {
    IWorkflowService workflowService;

    @GetMapping("")
    public ApiResponse<PageResponse<WorkflowResponse>> getByComplexId(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "50") int pageSize) {

        String complexId = getCurrentComplexId();
        PageResponse<WorkflowResponse> response = workflowService.getByComplexId(complexId, pageNumber, pageSize);
        return ApiResponse.<PageResponse<WorkflowResponse>>builder()
                .result(response)
                .build();
    }

    @PostMapping("")
    public ApiResponse<String> create(
            @RequestBody WorkflowRequest request) {
        String complexId = getCurrentComplexId();
        return ApiResponse.<String>builder()
                .result(workflowService.createWorkflow(complexId, request))
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }

}
