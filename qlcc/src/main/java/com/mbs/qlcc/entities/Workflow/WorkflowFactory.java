package com.mbs.qlcc.entities.Workflow;

import java.time.LocalDateTime;

public class WorkflowFactory implements IWorkflowFactory {
    @Override
    public Workflow createWorkflow(String complexId, String workflowName, String description, int status) {
        return new Workflow(complexId, workflowName, description, status, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
