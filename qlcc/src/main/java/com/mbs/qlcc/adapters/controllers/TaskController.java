package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Task.CreateTaskRequest;
import com.mbs.qlcc.adapters.request.Task.TaskFilterRequest;
import com.mbs.qlcc.adapters.request.Task.TaskUpdateRequest;
import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.TaskService;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskHistoryResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskSummaryResponse;
import com.mbs.qlcc.usecases.response.Task.TaskResponse;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;

    @PostMapping
    public ApiResponse<TaskResponse> create(@ModelAttribute CreateTaskRequest request) throws IOException {
        String complexId = getCurrentComplexId();
        String userId = getCurrentUserId();
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.create(request, complexId, userId))
                .build();
    }

    @PostMapping("/org/{taskStatus}/{orgId}")
    public ApiResponse<PageResponse<ITaskOrgResponse>> findByOrgId(@RequestBody TaskFilterRequest request,
                                                                   @PathVariable int taskStatus, @PathVariable String orgId) {
        String userId = getCurrentUserId();
        return ApiResponse.<PageResponse<ITaskOrgResponse>>builder()
                .result(taskService.getTasksByOrgId(request, userId, orgId, taskStatus))
                .build();
    }

    @PostMapping("/creator/{taskStatus}")
    public ApiResponse<PageResponse<ITaskOrgResponse>> findByCreator(@RequestBody TaskFilterRequest request, @PathVariable String taskStatus) {
        String userId = getCurrentUserId();
        return ApiResponse.<PageResponse<ITaskOrgResponse>>builder()
                .result(taskService.getTasksByCreator(request, userId, taskStatus))
                .build();
    }

    @GetMapping("/workflow/{taskId}")
    public ApiResponse<List<ITaskHistoryResponse>> findWfByTaskId(@PathVariable String taskId) {
        return ApiResponse.<List<ITaskHistoryResponse>>builder()
                .result(taskService.findWfByTaskId(taskId))
                .build();
    }

    @GetMapping("/task-summary")
    public ApiResponse<List<ITaskSummaryResponse>> taskActionSummary() {
        String orgId = getCurrentOrgId();
        String userId = getCurrentUserId();
        return ApiResponse.<List<ITaskSummaryResponse>>builder()
                .result(taskService.taskActionSummary(orgId, userId))
                .build();
    }

    @PostMapping("/filter-task/{orgId}")
    public ApiResponse<PageResponse<ITaskOrgResponse>> filterTaskApproved(@RequestBody TaskFilterRequest request, @PathVariable String orgId) {
        String userId = getCurrentUserId();
        return ApiResponse.<PageResponse<ITaskOrgResponse>>builder()
                .result(taskService.filterTaskApproved(request, userId, orgId, Constant.APPROVED.getValue()))
                .build();
    }

    @PutMapping("/approval/{id}")
    public ApiResponse<String> approveTask(
            @RequestBody TaskUpdateRequest request,
            @PathVariable String id
    ) {
        String complexId = getCurrentComplexId();
        String userId = getCurrentUserId();
        taskService.approveTask(request, id, userId, complexId);
        return ApiResponse.<String>builder()
                .result("Task approved successfully!")
                .build();
    }

    @PutMapping("/rejection/{id}")
    public ApiResponse<String> rejectTask(
            @RequestBody TaskUpdateRequest request,
            @PathVariable String id
    ) {
        String complexId = getCurrentComplexId();
        String userId = getCurrentUserId();
        taskService.rejectTask(request, id, userId, complexId);
        return ApiResponse.<String>builder()
                .result("Task rejected successfully!")
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }

    private String getCurrentOrgId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("org_id").toString();
    }

    private String getCurrentUserId() {
        return JwtUtil.getUserId(JwtUtil.getToken());
    }

}
