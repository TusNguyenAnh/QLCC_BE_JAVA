package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Organization.CreateOrganizationRequest;
import com.mbs.qlcc.dto.request.TaskType.TaskTypeRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.Organization.OrganizationResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Task.ITaskTypeResponse;
import com.mbs.qlcc.dto.response.Task.TaskTypeResponse;
import com.mbs.qlcc.service.ITaskTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task-type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskTypeController {
    ITaskTypeService taskTypeService;

    @GetMapping
    public ApiResponse<PageResponse<ITaskTypeResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int perPage) {

        return ApiResponse.<PageResponse<ITaskTypeResponse>>builder()
                .result(taskTypeService.getAllTaskType(page, Math.min(perPage, 50)))
                .build();
    }

    @PostMapping
    public ApiResponse<TaskTypeResponse> create(
            @RequestBody TaskTypeRequest request) {
        return ApiResponse.<TaskTypeResponse>builder()
                .result(taskTypeService.create(request))
                .build();
    }
}
