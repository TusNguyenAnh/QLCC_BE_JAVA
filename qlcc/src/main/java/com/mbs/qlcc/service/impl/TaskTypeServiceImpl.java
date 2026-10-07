package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Organization;
import com.mbs.qlcc.domain.Priority;
import com.mbs.qlcc.domain.TaskType;
import com.mbs.qlcc.domain.Workflow;
import com.mbs.qlcc.dto.request.TaskType.TaskTypeRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Task.ITaskTypeResponse;
import com.mbs.qlcc.dto.response.Task.TaskTypeResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Organization.OrganizationMapper;
import com.mbs.qlcc.mapper.Task.TaskTypeMapper;
import com.mbs.qlcc.repository.Priority.IPriorityRepository;
import com.mbs.qlcc.repository.Task.ITaskTypeRepository;
import com.mbs.qlcc.repository.Workflow.IWorkflowRepository;
import com.mbs.qlcc.service.ITaskTypeService;
import com.mbs.qlcc.utils.ErrorCode;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskTypeServiceImpl implements ITaskTypeService {
    ITaskTypeRepository taskTypeRepository;
    IWorkflowRepository workflowRepository;
    IPriorityRepository priorityRepository;

    @Override
    public TaskTypeResponse create(TaskTypeRequest request) {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        Workflow workflow = workflowRepository.findById(request.getWorkflowId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Priority priority = priorityRepository.findById(request.getPriorityId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        TaskType taskType = TaskType.builder()
                .complexId(complexId)
                .workflow(workflow)
                .priority(priority)
                .typeName(request.getTypeName())
                .description(request.getDescription())
                .status(0)
                .isDeleted(false)
                .build();

        return TaskTypeMapper.toResponse(taskTypeRepository.save(taskType));
    }

    @Override
    public PageResponse<ITaskTypeResponse> getAllTaskType(int page, int perPage) {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.ASC, "createdAt");
        Page<ITaskTypeResponse> result = taskTypeRepository
                .findByComplexIdAndDeletedAtIsNull(complexId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
