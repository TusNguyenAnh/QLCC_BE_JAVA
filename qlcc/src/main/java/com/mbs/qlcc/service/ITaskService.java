package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Task.CreateTaskRequest;
import com.mbs.qlcc.dto.request.Task.TaskFilterRequest;
import com.mbs.qlcc.dto.request.Task.TaskUpdateRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Task.ITaskHistoryResponse;
import com.mbs.qlcc.dto.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.dto.response.Task.ITaskSummaryResponse;
import com.mbs.qlcc.dto.response.Task.TaskResponse;

import java.io.IOException;
import java.util.List;

public interface ITaskService {
    TaskResponse create(CreateTaskRequest request, String complexId, String userId) throws IOException;

    PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterRequest request, String approverId, String orgId, int status);

    PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterRequest request, String creator, String status);

    void approveTask(TaskUpdateRequest request, String taskId, String approverId, String complexId);

    void rejectTask(TaskUpdateRequest request, String taskId, String approverId, String complexId);

    PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterRequest request, String approverId, String orgId, String status);

    List<ITaskHistoryResponse> findWfByTaskId(String taskId);

    List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId);
}
