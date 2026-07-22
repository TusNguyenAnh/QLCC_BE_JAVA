package com.mbs.qlcc.adapters.db.Task;

import com.mbs.qlcc.adapters.db.Priority.PriorityDataMapper;
import com.mbs.qlcc.adapters.db.Workflow.WorkflowDataMapper;
import com.mbs.qlcc.entities.Task.TaskType;
import com.mbs.qlcc.usecases.output.Task.ITaskTypeDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaTaskType implements ITaskTypeDsGateway {
    private final JpaTaskTypeRepository repository;

    @Override
    public TaskType getTaskTypeById(String id) {
        TaskTypeDataMapper dataMapper = repository.findById(id).orElse(null);
        return toTaskType(dataMapper);
    }

    @Override
    public TaskType createTaskType(TaskType taskType) {
        TaskTypeDataMapper dataMapper = TaskTypeDataMapper.builder()
                .complexId(taskType.getComplexId())
                .workflow(WorkflowDataMapper.builder().id(taskType.getWorkflowId()).build())
                .priority(PriorityDataMapper.builder().id(taskType.getPriorityId()).build())
                .status(taskType.getStatus())
                .typeName(taskType.getTypeName())
                .description(taskType.getDescription())
                .createdAt(taskType.getCreatedAt())
                .updatedAt(taskType.getUpdatedAt())
                .deletedAt(taskType.getDeletedAt())
                .isDeleted(taskType.isDeleted())
                .build();
        return toTaskType(repository.save(dataMapper));
    }

    @Override
    public PageResponse<TaskType> getTaskTypesByComplexId(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "created_at"
        );

        Page<TaskTypeDataMapper> result = repository.findByComplexId(complexId, pageable);
        return new PageResponse<TaskType>(
                result.getContent().stream().map(this::toTaskType).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    private TaskType toTaskType(TaskTypeDataMapper dataMapper) {
        if (dataMapper == null) {
            return null;
        }
        return new TaskType(dataMapper.getId(), dataMapper.getComplexId(), dataMapper.getWorkflow().getId(),
                dataMapper.getPriority().getId(), dataMapper.getStatus(), dataMapper.getTypeName(), dataMapper.getDescription(),
                dataMapper.getCreatedAt(), dataMapper.getUpdatedAt(), dataMapper.getDeletedAt(), dataMapper.isDeleted());
    }
}
