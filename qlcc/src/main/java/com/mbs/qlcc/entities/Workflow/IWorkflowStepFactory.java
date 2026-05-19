package com.mbs.qlcc.entities.Workflow;

public interface IWorkflowStepFactory {
    WorkflowStep createWorkflowStep(String id,String workflowId, int stepOrder, int orgLevel, String description, int status);
}
