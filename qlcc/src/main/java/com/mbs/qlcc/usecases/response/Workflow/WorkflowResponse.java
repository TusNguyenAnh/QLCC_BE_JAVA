package com.mbs.qlcc.usecases.response.Workflow;

import com.mbs.qlcc.usecases.response.Task.ITaskTypeResponse;

import java.util.List;

public interface WorkflowResponse {
    String getId();

    String getComplexId();

    String getWorkflowName();

    String getDescription();

    int getStatus();
    List<ITaskTypeResponse> getTaskType();

    List<WorkflowStepResponse> getWorkflowSteps();
}
