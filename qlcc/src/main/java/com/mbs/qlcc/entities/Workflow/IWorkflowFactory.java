package com.mbs.qlcc.entities.Workflow;

public interface IWorkflowFactory {
    Workflow createWorkflow(String complexId, String workflowName, String description, int status);
}
