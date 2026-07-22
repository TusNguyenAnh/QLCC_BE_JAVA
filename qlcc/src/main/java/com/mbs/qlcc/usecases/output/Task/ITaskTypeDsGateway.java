package com.mbs.qlcc.usecases.output.Task;

import com.mbs.qlcc.entities.Task.TaskType;
import com.mbs.qlcc.usecases.response.PageResponse;

public interface ITaskTypeDsGateway {
    TaskType getTaskTypeById(String id);
    TaskType createTaskType(TaskType taskType);
    PageResponse<TaskType> getTaskTypesByComplexId(String complexId, int page, int size);
}
