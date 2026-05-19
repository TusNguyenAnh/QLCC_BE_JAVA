package com.mbs.qlcc.usecases.output.Workflow;

import com.mbs.qlcc.entities.Workflow.WorkflowStepApprover;

import java.util.List;

public interface IWorkflowStepApproverDsGateway {
    List<String> getPositionByWfStep(String wfStepId);
    void saveAll(List<WorkflowStepApprover> workflowStepApprovers);
}
