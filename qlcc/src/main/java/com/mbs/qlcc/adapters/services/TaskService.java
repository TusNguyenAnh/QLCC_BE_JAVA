package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Task.CreateTaskRequest;
import com.mbs.qlcc.adapters.request.Task.TaskFilterRequest;
import com.mbs.qlcc.adapters.request.Task.TaskUpdateRequest;
import com.mbs.qlcc.usecases.input.ITaskInputBoundary;
import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.request.Task.TaskInpRequest;
import com.mbs.qlcc.usecases.request.Task.TaskUpdateInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    ITaskInputBoundary taskInteractor;
    MediaFileService mediaFileService;

    public TaskResponse create(CreateTaskRequest request, String complexId, String userId) throws IOException {
        System.out.println(request.getBuildingId());
        TaskInpRequest inpRequest = new TaskInpRequest(
                request.getTasktypeId(),
                request.getBuildingId(),
                request.getTaskName(),
                request.getDescription(),
                request.getCategory(),
                userId,
                complexId
        );
        TaskResponse taskResponse = taskInteractor.createTask(inpRequest);
        mediaFileService.create(request.getFiles(), "task", taskResponse.getId());
        return taskResponse;
    }

    public PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterRequest request, String approverId, String orgId, int status) {
        TaskFilterInpRequest filterRequest = new TaskFilterInpRequest(
                request.getPriorityId(),
                request.getTaskTypeId(),
                request.getTimeApprovedStart(),
                request.getTimeApprovedEnd(),
                request.getTimeRequestEnd(),
                request.getTimeRequestStart(),
                request.getOrder()
        );
        return taskInteractor.getTasksByOrgId(filterRequest, approverId, orgId, status, request.getPageNumber(), request.getPageSize());
    }

    public PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterRequest request, String creator, String status) {
        TaskFilterInpRequest filterRequest = new TaskFilterInpRequest(
                request.getPriorityId(),
                request.getTaskTypeId(),
                request.getTimeApprovedStart(),
                request.getTimeApprovedEnd(),
                request.getTimeRequestEnd(),
                request.getTimeRequestStart(),
                request.getOrder()
        );
        return taskInteractor.getTasksByCreator(filterRequest, creator, status, request.getPageNumber(), request.getPageSize());
    }

    @Transactional
    public void approveTask(TaskUpdateRequest request, String taskId, String approverId, String complexId) {
        TaskUpdateInpRequest inpRequest = new TaskUpdateInpRequest(
                request.getAction(),
                request.getComment(),
                approverId,
                complexId
        );
        taskInteractor.approveTask(inpRequest, taskId);
    }

    @Transactional
    public void rejectTask(TaskUpdateRequest request, String taskId, String approverId, String complexId) {
        TaskUpdateInpRequest inpRequest = new TaskUpdateInpRequest(
                request.getAction(),
                request.getComment(),
                approverId,
                complexId
        );
        taskInteractor.rejectTask(inpRequest, taskId);
    }

    public PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterRequest request, String approverId, String orgId, String status) {
        TaskFilterInpRequest filterRequest = new TaskFilterInpRequest(
                request.getPriorityId(),
                request.getTaskTypeId(),
                request.getTimeApprovedStart(),
                request.getTimeApprovedEnd(),
                request.getTimeRequestEnd(),
                request.getTimeRequestStart(),
                request.getOrder()
        );
        return taskInteractor.filterTaskApproved(filterRequest, approverId, orgId, status, request.getPageNumber(), request.getPageSize());
    }

    public List<ITaskHistoryResponse> findWfByTaskId(String taskId) {
        return taskInteractor.findWfByTaskId(taskId);
    }

    public  List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId) {
        return taskInteractor.taskActionSummary(orgId, approverId);
    }

}
