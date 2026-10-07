package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Expense;
import com.mbs.qlcc.domain.Revenue;
import com.mbs.qlcc.repository.Expense.IExpenseRepository;
import com.mbs.qlcc.repository.Revenue.IRevenueRepository;
import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.Task;
import com.mbs.qlcc.domain.TaskHistory;
import com.mbs.qlcc.domain.TaskType;
import com.mbs.qlcc.domain.WorkflowStep;
import com.mbs.qlcc.dto.request.Task.CreateTaskRequest;
import com.mbs.qlcc.dto.request.Task.TaskFilterRequest;
import com.mbs.qlcc.dto.request.Task.TaskUpdateRequest;
import com.mbs.qlcc.dto.response.Organization.IOrgBuildingResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Task.ITaskHistoryResponse;
import com.mbs.qlcc.dto.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.dto.response.Task.ITaskSummaryResponse;
import com.mbs.qlcc.dto.response.Task.TaskResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Task.TaskMapper;
import com.mbs.qlcc.repository.Organization.IOrgBuildingRepository;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.Task.ITaskHistoryRepository;
import com.mbs.qlcc.repository.Task.ITaskRepository;
import com.mbs.qlcc.repository.Task.ITaskTypeRepository;
import com.mbs.qlcc.repository.Workflow.IWorkflowStepApproverRepository;
import com.mbs.qlcc.repository.Workflow.IWorkflowStepRepository;
import com.mbs.qlcc.service.IMediaFileService;
import com.mbs.qlcc.service.ITaskService;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements ITaskService {

    ITaskRepository taskRepository;
    ITaskHistoryRepository taskHistoryRepository;
    ITaskTypeRepository taskTypeRepository;
    IWorkflowStepRepository workflowStepRepository;
    IWorkflowStepApproverRepository workflowStepApproverRepository;
    IOrgBuildingRepository orgBuildingRepository;
    IOrgUserRepository orgUserRepository;
    IExpenseRepository expenseRepository;
    IRevenueRepository revenueRepository;
    IMediaFileService mediaFileService;
    TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse create(CreateTaskRequest request, String complexId, String userId) throws IOException {
        try {
            TaskType taskType = taskTypeRepository.findByIdAndDeletedAtIsNull(request.getTasktypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.TASK_INFO_INVALID));
            // Lay ra wf cua task
            String wfId = taskType.getWorkflow().getId();

            // lay ra cac buoc xet duyet cua task
            List<WorkflowStep> workflowSteps = workflowStepRepository.findByWorkflow_IdOrderByStepOrderAsc(wfId);
            if (workflowSteps.isEmpty()) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            // lay ra role xet duyet o tung buoc
            Map<String, List<String>> workflowStepApprovers = workflowSteps.stream().collect(Collectors.toMap(
                    WorkflowStep::getId,
                    step -> workflowStepApproverRepository.findRoleIdsByWorkflowStepId(step.getId())
            ));

            // lay ra tat ca cap xet duyet cu the de resolve cho workflow step va tao map: key = level, value = org_id
            Map<Integer, String> orgs = orgBuildingRepository.findOrgsByAllBuildings(request.getBuildingId(), request.getBuildingId().size()).stream().collect(
                    Collectors.toMap(
                            IOrgBuildingResponse::getLevel,
                            IOrgBuildingResponse::getOrgId
                    )
            );

            // resolve org_id cu the se xet duyet task
            Map<String, String> wfStepResolveOrg = workflowSteps.stream().collect(Collectors.toMap(
                    WorkflowStep::getId,
                    step -> {
                        String orgId = orgs.get(step.getOrgLevel());
                        if (orgId != null) {
                            return orgId;
                        }
                        throw new AppException(ErrorCode.NOT_FOUND);
                    }
            ));

            // resolve user_id cu the se xet duyet task
            Map<String, List<String>> wfStepResolveUser = workflowSteps.stream().collect(Collectors.toMap(
                    WorkflowStep::getId,
                    step -> {
                        String orgId = wfStepResolveOrg.get(step.getId());
                        List<String> roles = workflowStepApprovers.get(step.getId());
                        if (orgId != null && roles != null && !roles.isEmpty()) {
                            List<String> userIds = orgUserRepository.findByOrgIdAndRoleIdIn(orgId, roles)
                                    .stream()
                                    .map(OrgUser::getUserId)
                                    .toList();
                            if (!userIds.isEmpty()) {
                                return userIds;
                            }
                        }
                        throw new AppException(ErrorCode.NOT_FOUND);
                    }
            ));

            String currentOrgId = wfStepResolveOrg.get(workflowSteps.get(0).getId());
            Integer currentStep = workflowSteps.get(0).getStepOrder();

            Task task = Task.builder()
                    .complexId(complexId)
                    .taskTypeId(request.getTasktypeId())
                    .currentOrgId(currentOrgId)
                    .creator(userId)
                    .currentStep(currentStep)
                    .taskName(request.getTaskName())
                    .description(request.getDescription())
                    .status(Constant.PENDING.getValue())
                    .category(request.getCategory())
                    .isDeleted(false)
                    .build();

            Task savedTask = taskRepository.save(task);

            List<TaskHistory> taskHistories = new ArrayList<>();
            for (WorkflowStep step : workflowSteps) {
                List<String> usersInStep = wfStepResolveUser.get(step.getId());
                String orgInStep = wfStepResolveOrg.get(step.getId());
                for (String approverId : usersInStep) {
                    taskHistories.add(TaskHistory.builder()
                            .taskId(savedTask.getId())
                            .approverId(approverId)
                            .orgId(orgInStep)
                            .stepOrder(step.getStepOrder())
                            .action(Constant.PENDING.getValue())
                            .comment("")
                            .isDeleted(false)
                            .build());
                }
            }
            taskHistoryRepository.saveAll(taskHistories);

            if (request.getFiles() != null && !request.getFiles().isEmpty()) {
                mediaFileService.create(request.getFiles(), "task", savedTask.getId());
            }

            return taskMapper.toResponse(savedTask);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.TASK_INFO_INVALID);
        }
    }

    @Override
    public PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterRequest request, String approverId, String orgId, int status) {
        String taskStatus = status == 2 ? Constant.PENDING.getValue() : Constant.REJECT.getValue();
        int pageNumber = request.getPageNumber() > 0 ? request.getPageNumber() - 1 : 0;

        Sort.Direction direction = "ASC".equalsIgnoreCase(request.getOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, request.getPageSize(), Sort.by(direction, "created_at"));

        Object priorityIds = (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) ? request.getPriorityId() : null;
        String checkPriority = (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) ? "priorityIds" : null;
        Object taskTypeIds = (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) ? request.getTaskTypeId() : null;
        String checkTaskType = (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) ? "taskTypeIds" : null;


        Page<ITaskOrgResponse> pageResult = taskRepository.getByOrgId(
                orgId,
                taskStatus,
                approverId,
                checkPriority,
                checkTaskType,
                priorityIds,
                taskTypeIds,
                request.getTimeApprovedStart(),
                request.getTimeApprovedEnd(),
                request.getTimeRequestStart(),
                request.getTimeRequestEnd(),
                pageable
        );

        return new PageResponse<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterRequest request, String creator, String status) {
        int pageNumber = request.getPageNumber() > 0 ? request.getPageNumber() - 1 : 0;
        Sort.Direction direction = "ASC".equalsIgnoreCase(request.getOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, request.getPageSize(), Sort.by(direction, "created_at"));

        Object priorityIds = (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) ? request.getPriorityId() : null;
        Object taskTypeIds = (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) ? request.getTaskTypeId() : null;
        String checkPriority = (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) ? "priorityIds" : null;
        String checkTaskType = (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) ? "taskTypeIds" : null;

        Page<ITaskOrgResponse> pageResult = taskRepository.getByCreator(
                creator,
                status,
                checkPriority,
                checkTaskType,
                priorityIds,
                taskTypeIds,
                request.getTimeApprovedStart(),
                request.getTimeApprovedEnd(),
                request.getTimeRequestStart(),
                request.getTimeRequestEnd(),
                pageable
        );

        return new PageResponse<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    @Transactional
    public void approveTask(TaskUpdateRequest request, String taskId, String approverId, String complexId) {
        try {
            Task existingTask = taskRepository.findByIdAndDeletedAtIsNull(taskId)
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            String currentOrgId = existingTask.getCurrentOrgId();
            boolean isStepOrder = existingTask.getCurrentStep() != request.getStepOrder();
            TaskHistory existingTaskHistory = taskHistoryRepository.findByTaskIdAndOrgIdAndApproverIdAndStepOrder(taskId, currentOrgId, approverId, request.getStepOrder());
            if (existingTaskHistory == null || !existingTaskHistory.getAction().equals(Constant.PENDING.getValue()) || isStepOrder) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            if (request.getAction() != null && !request.getAction().isEmpty() && !request.getAction().equals(existingTaskHistory.getAction())) {
                existingTaskHistory.setAction(request.getAction());
            }

            if (request.getComment() != null && !request.getComment().isEmpty() && !request.getComment().equals(existingTaskHistory.getComment())) {
                existingTaskHistory.setComment(request.getComment());
            }

            if (approverId != null && !approverId.isEmpty() && !approverId.equals(existingTaskHistory.getApproverId())) {
                existingTaskHistory.setApproverId(approverId);
            }

            taskHistoryRepository.save(existingTaskHistory);

            boolean hasUnapprovedInStep = taskHistoryRepository.checkAllApprovedInStep(taskId, existingTask.getCurrentStep(), Constant.APPROVED.getValue());

            if (!hasUnapprovedInStep) {
                int nextStep = existingTask.getCurrentStep() + 1;
                TaskHistory nextTaskHistory = taskHistoryRepository.findFirstByTaskIdAndStepOrder(taskId, nextStep);
                if (nextTaskHistory != null) {
                    existingTask.setCurrentStep(nextTaskHistory.getStepOrder());
                    existingTask.setCurrentOrgId(nextTaskHistory.getOrgId());
                    existingTask.setStatus(Constant.PENDING.getValue());
                } else {
                    existingTask.setStatus(Constant.APPROVED.getValue());
                }
                taskRepository.save(existingTask);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.TASK_INFO_INVALID);
        }
    }

    @Override
    @Transactional
    public void rejectTask(TaskUpdateRequest request, String taskId, String approverId, String complexId) {
        try {
            Task existingTask = taskRepository.findByIdAndDeletedAtIsNull(taskId)
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            String currentOrgId = existingTask.getCurrentOrgId();
            boolean isStepOrder = existingTask.getCurrentStep() != request.getStepOrder();

            List<TaskHistory> taskHistoriesForReject = taskHistoryRepository.findTaskForReject(taskId, existingTask.getCurrentStep());
            taskHistoriesForReject.forEach(th -> th.setAction(Constant.UNFINISHED.getValue()));
            taskHistoryRepository.saveAll(taskHistoriesForReject);

            TaskHistory existingTaskHistory = taskHistoryRepository.findByTaskIdAndOrgIdAndApproverIdAndStepOrder(taskId, currentOrgId, approverId, request.getStepOrder());
            if (existingTaskHistory == null || !existingTaskHistory.getAction().equals(Constant.PENDING.getValue()) || isStepOrder) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            if (request.getAction() != null && !request.getAction().isEmpty() && !request.getAction().equals(existingTaskHistory.getAction())) {
                existingTaskHistory.setAction(request.getAction());
            }

            if (request.getComment() != null && !request.getComment().isEmpty() && !request.getComment().equals(existingTaskHistory.getComment())) {
                existingTaskHistory.setComment(request.getComment());
            }

            if (approverId != null && !approverId.isEmpty() && !approverId.equals(existingTaskHistory.getApproverId())) {
                existingTaskHistory.setApproverId(approverId);
            }

            taskHistoryRepository.save(existingTaskHistory);

            existingTask.setStatus(Constant.REJECT.getValue());
            taskRepository.save(existingTask);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.TASK_INFO_INVALID);
        }
    }

    @Override
    public PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterRequest request, String approverId, String orgId, String status) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(request.getOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        int pageNumber = request.getPageNumber() > 0 ? request.getPageNumber() - 1 : 0;

        Pageable pageable = PageRequest.of(pageNumber, request.getPageSize(), Sort.by(direction, "updated_at"));

        Object priorityIds = (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) ? request.getPriorityId() : null;
        Object taskTypeIds = (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) ? request.getTaskTypeId() : null;
        String checkPriority = (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) ? "priorityIds" : null;
        String checkTaskType = (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) ? "taskTypeIds" : null;

        Page<ITaskOrgResponse> pageResult = taskHistoryRepository.filterTaskApproved(
                orgId,
                status,
                approverId,
                checkPriority,
                checkTaskType,
                priorityIds,
                taskTypeIds,
                request.getTimeApprovedStart(),
                request.getTimeApprovedEnd(),
                request.getTimeRequestStart(),
                request.getTimeRequestEnd(),
                pageable
        );

        return new PageResponse<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public List<ITaskHistoryResponse> findWfByTaskId(String taskId) {
        return taskHistoryRepository.getByTaskId(taskId);
    }

    @Override
    public List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId) {
        return taskHistoryRepository.taskActionSummary(orgId, approverId);
    }
}
