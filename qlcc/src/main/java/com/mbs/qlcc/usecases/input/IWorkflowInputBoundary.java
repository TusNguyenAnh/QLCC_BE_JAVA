package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Workflow.WorkflowInpRequest;
import com.mbs.qlcc.usecases.request.Workflow.WorkflowStepInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Workflow.WorkflowResponse;

public interface IWorkflowInputBoundary {
    PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size);
    String createWorkflow(WorkflowInpRequest request);
}
