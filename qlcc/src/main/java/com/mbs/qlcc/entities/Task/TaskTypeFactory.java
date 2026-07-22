package com.mbs.qlcc.entities.Task;

import java.time.LocalDateTime;

public class TaskTypeFactory implements ITaskTypeFactory {
    @Override
    public TaskType createTaskType(String complexId, String workflowId, String priorityId, Integer status, String typeName, String description) {
        return new TaskType(complexId, workflowId, priorityId, status, typeName, description, LocalDateTime.now(), LocalDateTime.now(), null, false);
    }
}
