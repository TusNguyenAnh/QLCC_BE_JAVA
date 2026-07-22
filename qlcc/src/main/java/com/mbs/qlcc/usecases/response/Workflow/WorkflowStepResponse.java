package com.mbs.qlcc.usecases.response.Workflow;

import java.util.List;
import java.util.Set;

public interface WorkflowStepResponse {
    String getId();

    int getOrgLevel();

    int getStepOrder();

    String getDescription();

    int getStatus();

    Set<WorkflowStepApproverResponse> getWorkflowStepApprovers();

}
