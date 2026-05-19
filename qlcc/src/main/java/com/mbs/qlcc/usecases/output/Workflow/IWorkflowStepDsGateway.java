package com.mbs.qlcc.usecases.output.Workflow;

import com.mbs.qlcc.entities.Workflow.WorkflowStep;

import java.util.List;

public interface IWorkflowStepDsGateway {
    List<WorkflowStep> getWorkflowStepsByWorkflowId(String workflowId);
    void saveAll(List<WorkflowStep> workflowSteps);
}
