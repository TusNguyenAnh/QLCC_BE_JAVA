package com.mbs.qlcc.entities.Workflow;

import java.time.LocalDateTime;

public class WorkflowStepApproverFactory implements IWorkflowStepApproverFactory {
    @Override
    public WorkflowStepApprover createWorkflowStepApprover(String workflowStepId, String position, int status) {
        return new WorkflowStepApprover(workflowStepId, position, status, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
