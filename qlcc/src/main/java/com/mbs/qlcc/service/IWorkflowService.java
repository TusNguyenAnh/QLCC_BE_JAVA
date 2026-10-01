package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Workflow.WorkflowRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Workflow.WorkflowResponse;

public interface IWorkflowService {

    PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size);

    String createWorkflow(String complexId, WorkflowRequest request);
}
