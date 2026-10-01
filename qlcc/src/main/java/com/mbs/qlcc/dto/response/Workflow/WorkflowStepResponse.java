package com.mbs.qlcc.dto.response.Workflow;

import java.util.Set;

public interface WorkflowStepResponse {
    String getId();

    int getOrgLevel();

    int getStepOrder();

    String getDescription();
    String getModuleCode();

    int getStatus();

    Set<WorkflowStepApproverResponse> getWorkflowStepApprovers();

}
