package com.mbs.qlcc.adapters.db.Task;

import com.mbs.qlcc.entities.Task.TaskHistory;
import com.mbs.qlcc.usecases.output.Task.ITaskHistoryDsGateway;
import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskHistoryResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JpaTaskHistory implements ITaskHistoryDsGateway {
    private final JpaTaskHistoryRepository repository;

    @Override
    public void createTaskHistory(List<TaskHistory> taskHistory) {
        List<TaskHistoryDataMapper> dataMappers = taskHistory.stream()
                .map(this::mapToDataMapper)
                .toList();
        repository.saveAll(dataMappers);
    }

    @Override
    public List<ITaskHistoryResponse> getTaskHistoryByTaskId(String taskId) {
        return repository.getByTaskId(taskId);
    }


    @Override
    public  List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId) {
        return repository.taskActionSummary(orgId, approverId);
    }

    @Override
    public TaskHistory getByTaskId(String taskId, int stepOrder) {
        return mapToEntity(repository.findFirstByTaskIdAndStepOrder(taskId, stepOrder));
    }

    @Override
    public List<TaskHistory> updateTaskHistory(List<TaskHistory> taskHistory) {
        List<TaskHistoryDataMapper> dataMappers = taskHistory.stream()
                .map(this::mapToDataMapper)
                .toList();
        return repository.saveAll(dataMappers).stream()
                .map(this::mapToEntity)
                .toList();
    }


    @Override
    public boolean checkAllApprovedInStep(String taskId, int stepOrder, String action) {
        return repository.checkAllApprovedInStep(taskId, stepOrder, action);
    }

    @Override
    public PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterInpRequest request, String approverId, String orgId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "t.updated_at"
        );

        Map<String, Object> params = new HashMap<>();
        if (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) {
            params.put("priorityIds", request.getPriorityId());
        }

        if (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) {
            params.put("taskTypeIds", request.getTaskTypeId());
        }

        if (request.getTimeApprovedStart() != null) {
            params.put("approvedStart", request.getTimeApprovedStart());
        }

        if (request.getTimeApprovedEnd() != null) {
            params.put("approvedEnd", request.getTimeApprovedEnd());
        }

        if (request.getTimeRequestStart() != null) {
            params.put("requestStart", request.getTimeRequestStart());
        }

        if (request.getTimeRequestEnd() != null) {
            params.put("requestEnd", request.getTimeRequestEnd());
        }

        Page<ITaskOrgResponse> result = repository.filterTaskApproved(
                orgId, status, approverId, params.get("priorityIds"),
                params.get("taskTypeIds"), params.get("approvedStart"),
                params.get("approvedEnd"), params.get("requestStart"),
                params.get("requestEnd"), pageable
        );

        return new PageResponse<ITaskOrgResponse>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }


    @Override
    public TaskHistory getByTaskIdAndOrgIdAndApproverId(String taskId, String orgId, String approverId) {
        return mapToEntity(repository.findByTaskIdAndOrgIdAndApproverId(taskId, orgId, approverId));
    }

    @Override
    public List<TaskHistory> getTaskForReject(String taskId, int stepOrder) {
        return repository.findTaskForReject(taskId, stepOrder).stream()
                .map(this::mapToEntity)
                .toList();
    }

    private TaskHistory mapToEntity(TaskHistoryDataMapper dataMapper) {
        return new TaskHistory(
                dataMapper.getId(),
                dataMapper.getTaskId(),
                dataMapper.getApproverId(),
                dataMapper.getOrgId(),
                dataMapper.getStepOrder(),
                dataMapper.getAction(),
                dataMapper.getComment(),
                dataMapper.getCreatedAt(),
                dataMapper.getUpdatedAt(),
                dataMapper.getDeletedAt(),
                dataMapper.isDeleted()
        );
    }

    private TaskHistoryDataMapper mapToDataMapper(TaskHistory taskHistory) {
        return TaskHistoryDataMapper.builder()
                .id(taskHistory.getId())
                .taskId(taskHistory.getTaskId())
                .approverId(taskHistory.getApproverId())
                .orgId(taskHistory.getOrgId())
                .stepOrder(taskHistory.getStepOrder())
                .action(taskHistory.getAction())
                .comment(taskHistory.getComment())
                .createdAt(taskHistory.getCreatedAt())
                .updatedAt(taskHistory.getUpdatedAt())
                .deletedAt(taskHistory.getDeletedAt())
                .isDeleted(taskHistory.isDeleted())
                .build();
    }
}
