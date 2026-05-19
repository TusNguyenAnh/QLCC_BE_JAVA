package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Workflow.*;
import com.mbs.qlcc.usecases.input.IWorkflowInputBoundary;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowDsGateway;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowStepApproverDsGateway;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowStepDsGateway;
import com.mbs.qlcc.usecases.request.Workflow.WorkflowInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Workflow.WorkflowResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkflowInteractor implements IWorkflowInputBoundary {
    IWorkflowDsGateway workflowDsGateway;
    IWorkflowStepDsGateway workflowStepDsGateway;
    IWorkflowStepApproverDsGateway workflowStepApproverDsGateway;
    IWorkflowFactory workflowFactory;
    IWorkflowStepFactory workflowStepFactory;
    IWorkflowStepApproverFactory workflowStepApproverFactory;

    public WorkflowInteractor(IWorkflowDsGateway workflowDsGateway, IWorkflowStepDsGateway workflowStepDsGateway, IWorkflowStepApproverDsGateway workflowStepApproverDsGateway, IWorkflowFactory workflowFactory, IWorkflowStepFactory workflowStepFactory, IWorkflowStepApproverFactory workflowStepApproverFactory) {
        this.workflowDsGateway = workflowDsGateway;
        this.workflowStepDsGateway = workflowStepDsGateway;
        this.workflowStepApproverDsGateway = workflowStepApproverDsGateway;
        this.workflowFactory = workflowFactory;
        this.workflowStepFactory = workflowStepFactory;
        this.workflowStepApproverFactory = workflowStepApproverFactory;
    }

    @Override
    public PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size) {
        return workflowDsGateway.getByComplexId(complexId, page, size);
    }

    @Override
    public String createWorkflow(WorkflowInpRequest request) {
        try {
            // tao workflow
            Workflow workflow = workflowFactory.createWorkflow(
                    request.getComplexId(),
                    request.getWorkflowName(),
                    request.getDescription(),
                    0
            );

            Workflow savedWorkflow = workflowDsGateway.save(workflow);

            // tao workflow step va workflow step approver
            if (!request.getWorkflowSteps().isEmpty()) {
                List<WorkflowStep> workflowSteps = new ArrayList<>();
                List<WorkflowStepApprover> workflowStepApprovers = new ArrayList<>();

                request.getWorkflowSteps().forEach(workflowStepInpRequest -> {
                    String stepId = UUID.randomUUID().toString();
                    WorkflowStep workflowStep = workflowStepFactory.createWorkflowStep(
                            stepId,
                            savedWorkflow.getId(),
                            workflowStepInpRequest.getStepOrder(),
                            workflowStepInpRequest.getOrgLevel(),
                            workflowStepInpRequest.getDescription(),
                            0
                    );
                    workflowSteps.add(workflowStep);

                    // tao workflow step approver
                    if (!workflowStepInpRequest.getPositions().isEmpty()) {
                        workflowStepInpRequest.getPositions().forEach(approver -> {
                            WorkflowStepApprover workflowStepApprover = workflowStepApproverFactory.createWorkflowStepApprover(
                                    stepId,
                                    approver,
                                    0
                            );
                            workflowStepApprovers.add(workflowStepApprover);
                        });
                    }
                });
                workflowStepDsGateway.saveAll(workflowSteps);
                workflowStepApproverDsGateway.saveAll(workflowStepApprovers);
            }
            return "Create workflow successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
