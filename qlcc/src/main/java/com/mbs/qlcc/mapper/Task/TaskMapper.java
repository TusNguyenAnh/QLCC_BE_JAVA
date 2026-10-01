package com.mbs.qlcc.mapper.Task;

import com.mbs.qlcc.domain.Task;
import com.mbs.qlcc.domain.TaskHistory;
import com.mbs.qlcc.dto.response.Task.TaskHistoryResponse;
import com.mbs.qlcc.dto.response.Task.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponse(
                task.getId(),
                task.getComplexId(),
                task.getTaskTypeId(),
                task.getCurrentOrgId(),
                task.getCreator(),
                task.getCurrentStep(),
                task.getTaskName(),
                task.getDescription(),
                task.getStatus(),
                task.getCategory()
        );
    }

    public TaskHistoryResponse toHistoryResponse(TaskHistory history) {
        if (history == null) {
            return null;
        }
        return new TaskHistoryResponse(
                history.getId(),
                history.getTaskId(),
                history.getApproverId(),
                history.getOrgId(),
                history.getStepOrder(),
                history.getAction(),
                history.getComment()
        );
    }
}
