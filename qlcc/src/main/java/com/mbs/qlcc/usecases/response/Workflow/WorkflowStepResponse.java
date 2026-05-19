package com.mbs.qlcc.usecases.response.Workflow;

import java.util.List;

public interface WorkflowStepResponse {
    String getId();

    int getOrgLevel();

    int getStepOrder();

    String getDescription();

    int getStatus();

    List<WorkflowStepApproverResponse> getWorkflowStepApprovers();

}
