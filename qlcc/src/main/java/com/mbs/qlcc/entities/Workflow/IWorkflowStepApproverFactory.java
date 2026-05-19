package com.mbs.qlcc.entities.Workflow;

public interface IWorkflowStepApproverFactory {
    WorkflowStepApprover createWorkflowStepApprover(String workflowStepId, String position, int status);
}
