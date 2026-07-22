package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Expense.Expense;
import com.mbs.qlcc.entities.Revenue.Revenue;
import com.mbs.qlcc.entities.Task.ITaskFactory;
import com.mbs.qlcc.entities.Task.ITaskHistoryFactory;
import com.mbs.qlcc.entities.Task.Task;
import com.mbs.qlcc.entities.Task.TaskHistory;
import com.mbs.qlcc.entities.Workflow.WorkflowStep;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.ITaskInputBoundary;
import com.mbs.qlcc.usecases.output.Expense.IExpenseDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgBuildingDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.Revenue.IRevenueDsGateway;
import com.mbs.qlcc.usecases.output.Task.ITaskDsGateway;
import com.mbs.qlcc.usecases.output.Task.ITaskHistoryDsGateway;
import com.mbs.qlcc.usecases.output.Task.ITaskTypeDsGateway;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowStepApproverDsGateway;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowStepDsGateway;
import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.request.Task.TaskInpRequest;
import com.mbs.qlcc.usecases.request.Task.TaskUpdateInpRequest;
import com.mbs.qlcc.usecases.response.Organization.IOrgBuildingResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.*;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskInteractor implements ITaskInputBoundary {
    ITaskDsGateway taskDsGateway;
    ITaskTypeDsGateway taskTypeDsGateway;
    IWorkflowStepDsGateway workflowStepDsGateway;
    IWorkflowStepApproverDsGateway workflowStepApproverDsGateway;
    IOrgBuildingDsGateway orgBuildingDsGateway;
    IOrgUserDsGateway orgUserDsGateway;
    ITaskHistoryDsGateway taskHistoryDsGateway;
    IRevenueDsGateway revenueDsGateway;
    IExpenseDsGateway expenseDsGateway;
    ITaskFactory taskFactory;
    ITaskHistoryFactory taskHistoryFactory;

    public TaskInteractor(ITaskDsGateway taskDsGateway, ITaskTypeDsGateway taskTypeDsGateway,
                          IWorkflowStepDsGateway workflowStepDsGateway, IWorkflowStepApproverDsGateway workflowStepApproverDsGateway,
                          IOrgBuildingDsGateway orgBuildingDsGateway, IOrgUserDsGateway orgUserDsGateway,
                          ITaskHistoryDsGateway taskHistoryDsGateway, IRevenueDsGateway revenueDsGateway,
                          IExpenseDsGateway expenseDsGateway, ITaskFactory taskFactory, ITaskHistoryFactory taskHistoryFactory) {
        this.taskDsGateway = taskDsGateway;
        this.taskTypeDsGateway = taskTypeDsGateway;
        this.workflowStepDsGateway = workflowStepDsGateway;
        this.workflowStepApproverDsGateway = workflowStepApproverDsGateway;
        this.orgBuildingDsGateway = orgBuildingDsGateway;
        this.orgUserDsGateway = orgUserDsGateway;
        this.taskHistoryDsGateway = taskHistoryDsGateway;
        this.revenueDsGateway = revenueDsGateway;
        this.expenseDsGateway = expenseDsGateway;
        this.taskFactory = taskFactory;
        this.taskHistoryFactory = taskHistoryFactory;
    }

    @Override
    public TaskResponse createTask(TaskInpRequest request) {
        try {
            // Lay ra wf cua task
            String wfId = taskTypeDsGateway.getTaskTypeById(request.getTasktypeId()).getWorkflowId();
            // lay ra cac buoc xet duyet cua task
            List<WorkflowStep> workflowSteps = workflowStepDsGateway.getWorkflowStepsByWorkflowId(wfId);
            // lay ra role xet duyet o tung buoc
            Map<String, List<String>> workflowStepApprovers = workflowSteps.stream().collect(Collectors.toMap(
                    WorkflowStep::getId,
                    step -> workflowStepApproverDsGateway.getPositionByWfStep(step.getId())
            ));
            // lay ra tat ca cap xet duyet cu the de resolve cho workflow step va tao map: key = level, value = org_id
            Map<Integer, String> orgs = orgBuildingDsGateway.findOrgsByAllBuildings(request.getBuildingId(), request.getBuildingId().size()).stream().collect(
                    Collectors.toMap(
                            IOrgBuildingResponse::getLevel,
                            IOrgBuildingResponse::getOrgId
                    )
            );
            // resolve org_id cu the se xet duyet task
            Map<String, String> wfStepResolveOrg = workflowSteps.stream().collect(Collectors.toMap(
                    WorkflowStep::getId,
                    (step) -> {
                        String orgId = orgs.get(step.getOrgLevel());
                        if (orgId != null) {
                            return orgs.get(step.getOrgLevel());
                        }
                        ;
                        throw new AppException(ErrorCode.NOT_FOUND);
                    }
            ));

            // resolve user_id cu the se xet duyet task
            Map<String, List<String>> wfStepResolveUser = workflowSteps.stream().collect(Collectors.toMap(
                    WorkflowStep::getId,
                    (step) -> {
                        String orgId = wfStepResolveOrg.get(step.getId());
                        List<String> roles = workflowStepApprovers.get(step.getId());
                        if (orgId != null && !roles.isEmpty()) {
                            return orgUserDsGateway.getAllUserIdsByOrgId(orgId, roles);
                        }
                        ;
                        throw new AppException(ErrorCode.NOT_FOUND);
                    }
            ));

            String currentOrgId = wfStepResolveOrg.get(workflowSteps.get(0).getId());
            Integer currentStep = workflowSteps.get(0).getStepOrder();
            Task createTask = taskDsGateway.createTask(taskFactory.createTask(request.getComplexId(), request.getTasktypeId(), currentOrgId,
                    request.getCreator(), currentStep, request.getTaskName(), request.getDescription(),
                    Constant.PENDING.getValue(), request.getCategory()));

            // luu task history
            List<TaskHistory> taskHistories = new ArrayList<>();
            for (WorkflowStep step : workflowSteps) {
                for (String userId : wfStepResolveUser.get(step.getId())) {
                    taskHistories.add((taskHistoryFactory.createTaskHistory(createTask.getId(), userId,
                            wfStepResolveOrg.get(step.getId()), step.getStepOrder(), Constant.PENDING.getValue(), "")));
                }
            }
            taskHistoryDsGateway.createTaskHistory(taskHistories);
            return mapToResponse(createTask);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.TASK_INFO_INVALID);
        }
    }

    @Override
    public void approveTask(TaskUpdateInpRequest request, String taskId) {
        try {
            Task existingTask = taskDsGateway.getTaskById(taskId);
            if (existingTask == null) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            String currentOrgId = existingTask.getCurrentOrgId();
            TaskHistory existingTaskHistory = taskHistoryDsGateway.getByTaskIdAndOrgIdAndApproverId(taskId, currentOrgId, request.getApproverId());
            if (existingTaskHistory == null) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            if (!request.getAction().isEmpty() && !request.getAction().equals(existingTaskHistory.getAction()))
                existingTaskHistory.setAction(request.getAction());

            if (!request.getComment().isEmpty() && !request.getComment().equals(existingTaskHistory.getComment()))
                existingTaskHistory.setComment(request.getComment());

            if (!request.getApproverId().isEmpty() && !request.getApproverId().equals(existingTaskHistory.getApproverId()))
                existingTaskHistory.setApproverId(request.getApproverId());

            taskHistoryDsGateway.updateTaskHistory(List.of(existingTaskHistory));

            boolean isApproved = taskHistoryDsGateway.checkAllApprovedInStep(taskId, existingTask.getCurrentStep(), Constant.APPROVED.getValue());

            if (!isApproved) {
                int nextStep = existingTask.getCurrentStep() + 1;
                TaskHistory nextTaskHistory = taskHistoryDsGateway.getByTaskId(taskId, nextStep);
                if (nextTaskHistory != null) {
                    existingTask.setCurrentStep(nextTaskHistory.getStepOrder());
                    existingTask.setCurrentOrgId(nextTaskHistory.getOrgId());
                    existingTask.setStatus(Constant.PENDING.getValue());
                } else {
                    existingTask.setStatus(Constant.APPROVED.getValue());

                    List<Expense> exitTaskExpense = expenseDsGateway.findExpensesByTaskId(taskId);
                    List<Revenue> exitTaskRevenue = revenueDsGateway.findRevenuesByTaskId(taskId);
                    if (!exitTaskExpense.isEmpty()) {
                        exitTaskExpense.forEach(
                                expense -> {
                                    expense.setApproved(1);
                                    expense.setApprovedAt(LocalDateTime.now());
                                    expense.setApprovedBy(request.getApproverId());
                                }
                        );
                        expenseDsGateway.updateExpense(exitTaskExpense);
                    }

                    if (!exitTaskRevenue.isEmpty()) {
                        exitTaskRevenue.forEach(
                                revenue -> {
                                    revenue.setApproved(1);
                                    revenue.setApprovedAt(LocalDateTime.now());
                                    revenue.setApprovedBy(request.getApproverId());
                                }
                        );
                        revenueDsGateway.updateRevenue(exitTaskRevenue);
                    }
                }
                taskDsGateway.updateTask(existingTask);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.TASK_INFO_INVALID);
        }
    }

    @Override
    public void rejectTask(TaskUpdateInpRequest request, String taskId) {
        try {
            Task existingTask = taskDsGateway.getTaskById(taskId);
            if (existingTask == null) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            String currentOrgId = existingTask.getCurrentOrgId();
            List<TaskHistory> taskHistoriesForReject = taskHistoryDsGateway.getTaskForReject(taskId, existingTask.getCurrentStep());
            taskHistoriesForReject.forEach(
                    taskHistory -> {
                        taskHistory.setAction(Constant.UNFINISHED.getValue());
                    }
            );

            //update cac task history lien quan den buoc dang xet duyet thanh UNFINISHED
            taskHistoryDsGateway.updateTaskHistory(taskHistoriesForReject);

            //update task history cua approver hien tai thanh REJECTED
            TaskHistory existingTaskHistory = taskHistoryDsGateway.getByTaskIdAndOrgIdAndApproverId(taskId, currentOrgId, request.getApproverId());
            if (existingTaskHistory == null) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            if (!request.getAction().isEmpty() && !request.getAction().equals(existingTaskHistory.getAction()))
                existingTaskHistory.setAction(request.getAction());

            if (!request.getComment().isEmpty() && !request.getComment().equals(existingTaskHistory.getComment()))
                existingTaskHistory.setComment(request.getComment());

            if (!request.getApproverId().isEmpty() && !request.getApproverId().equals(existingTaskHistory.getApproverId()))
                existingTaskHistory.setApproverId(request.getApproverId());

            taskHistoryDsGateway.updateTaskHistory(List.of(existingTaskHistory));

            existingTask.setStatus(Constant.REJECT.getValue());
            taskDsGateway.updateTask(existingTask);
        } catch (Exception e) {
            throw new AppException(ErrorCode.TASK_INFO_INVALID);
        }

    }

    @Override
    public PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterInpRequest request, String approverId, String
            orgId, int status, int page, int size) {
        String taskStatus = status == 2 ? Constant.PENDING.getValue() : Constant.REJECT.getValue();
        return taskDsGateway.getTasksByOrgId(request, approverId, orgId, taskStatus, page, size);
    }

    @Override
    public PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterInpRequest request, String creator, String
            status, int page, int size) {
        return taskDsGateway.getTasksByCreator(request, creator, status, page, size);
    }

    @Override
    public PageResponse<ITaskOrgResponse> filterTaskApproved(TaskFilterInpRequest request, String
            approverId, String orgId, String status, int page, int size) {
        return taskHistoryDsGateway.filterTaskApproved(request, approverId, orgId, status, page, size);
    }

    @Override
    public List<ITaskHistoryResponse> findWfByTaskId(String taskId) {
        return taskHistoryDsGateway.getTaskHistoryByTaskId(taskId);
    }

    @Override
    public  List<ITaskSummaryResponse> taskActionSummary(String orgId, String approverId) {
        return taskHistoryDsGateway.taskActionSummary(orgId, approverId);
    }


    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(task.getId(), task.getComplexId(), task.getTaskTypeId(), task.getCurrentOrgId(),
                task.getCreator(), task.getCurrentStep(), task.getTaskName(), task.getDescription(),
                task.getStatus(), task.getCategory());
    }

    private TaskHistoryResponse mapToHistoryResponse(TaskHistory taskHistory) {
        return new TaskHistoryResponse(taskHistory.getId(), taskHistory.getTaskId(), taskHistory.getApproverId(),
                taskHistory.getOrgId(), taskHistory.getStepOrder(), taskHistory.getAction(), taskHistory.getComment()
        );
    }


}
