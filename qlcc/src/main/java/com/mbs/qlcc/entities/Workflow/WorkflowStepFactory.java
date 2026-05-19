package com.mbs.qlcc.entities.Workflow;

import java.time.LocalDateTime;

public class WorkflowStepFactory implements IWorkflowStepFactory {
    @Override
    public WorkflowStep createWorkflowStep(String id, String workflowId, int stepOrder, int orgLevel, String description, int status) {
        return new WorkflowStep(id, workflowId, stepOrder, orgLevel, description, status, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
