package com.mbs.qlcc.usecases.output.Task;

import com.mbs.qlcc.entities.Task.TaskHistory;
import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskHistoryResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskSummaryResponse;

import java.util.List;

public interface ITaskHistoryDsGateway {
    void createTaskHistory(List<TaskHistory> taskHistory);

    List<ITaskHistoryResponse> getTaskHistoryByTaskId(String taskId);

    List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId);

    TaskHistory getByTaskId(String taskId, int stepOrder);

    List<TaskHistory> updateTaskHistory(List<TaskHistory> taskHistory);

    boolean checkAllApprovedInStep(String taskId, int stepOrder, String action);

    PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterInpRequest request, String approverId, String orgId, String status, int page, int size);

    TaskHistory getByTaskIdAndOrgIdAndApproverId(String taskId, String orgId, String approverId);

    List<TaskHistory> getTaskForReject(String taskId, int stepOrder);

}
