package com.mbs.qlcc.entities.Task;

public interface ITaskTypeFactory {
    TaskType createTaskType(String complexId, String workflowId, String priorityId, Integer status, String typeName, String description);
}
