package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.request.Task.TaskInpRequest;
import com.mbs.qlcc.usecases.request.Task.TaskUpdateInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.*;

import java.util.List;

public interface ITaskInputBoundary {
    TaskResponse createTask(TaskInpRequest request);

    void approveTask(TaskUpdateInpRequest request, String taskId);

    void rejectTask(TaskUpdateInpRequest request, String taskId);

    PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterInpRequest request, String approverId, String orgId, int status, int page, int size);

    PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterInpRequest request, String creator, String status, int page, int size);

    PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterInpRequest request, String approverId, String orgId, String status, int page, int size);

    List<ITaskHistoryResponse> findWfByTaskId(String taskId);

    List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId);

}
