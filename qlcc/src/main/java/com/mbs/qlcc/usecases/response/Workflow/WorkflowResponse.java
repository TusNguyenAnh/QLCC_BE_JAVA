package com.mbs.qlcc.usecases.response.Workflow;

import java.util.List;

public interface WorkflowResponse {
    String getId();

    String getComplexId();

    String getWorkflowName();

    String getDescription();

    int getStatus();

    List<WorkflowStepResponse> getWorkflowSteps();
}
