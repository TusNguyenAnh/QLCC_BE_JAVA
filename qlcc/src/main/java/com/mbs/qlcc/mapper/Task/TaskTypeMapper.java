package com.mbs.qlcc.mapper.Task;

import com.mbs.qlcc.domain.Priority;
import com.mbs.qlcc.domain.TaskType;
import com.mbs.qlcc.domain.Workflow;
import com.mbs.qlcc.dto.request.TaskType.TaskTypeRequest;
import com.mbs.qlcc.dto.response.Task.TaskTypeResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class TaskTypeMapper {
    public static TaskTypeResponse toResponse(TaskType taskType) {
        if (taskType == null) {
            return null;
        }

        String workflowId = taskType.getWorkflow() != null ? taskType.getWorkflow().getId() : null;
        String priorityId = taskType.getPriority() != null ? taskType.getPriority().getId() : null;

        return TaskTypeResponse.builder()
                .id(taskType.getId())
                .complexId(taskType.getComplexId())
                .workflowId(workflowId)
                .priorityId(priorityId)
                .typeName(taskType.getTypeName())
                .description(taskType.getDescription())
                .status(taskType.getStatus())
                .build();
    }

    public static TaskType toEntity(TaskTypeRequest request, String complexId, Workflow workflow, Priority priority) {
        if (request == null) {
            return null;
        }

        return TaskType.builder()
                .complexId(complexId)
                .workflow(workflow)
                .priority(priority)
                .typeName(request.getTypeName())
                .description(request.getDescription())
                .status(0)
                .isDeleted(false)
                .build();
    }

}
