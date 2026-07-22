package com.mbs.qlcc.usecases.output.Task;

import com.mbs.qlcc.entities.Task.Task;
import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;

public interface ITaskDsGateway {
    Task createTask(Task task);

    Task getTaskById(String taskId);

    Task updateTask(Task task);

    PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterInpRequest request, String approverId, String orgId, String status, int page, int size);

    PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterInpRequest request, String creator, String status, int page, int size);

}
