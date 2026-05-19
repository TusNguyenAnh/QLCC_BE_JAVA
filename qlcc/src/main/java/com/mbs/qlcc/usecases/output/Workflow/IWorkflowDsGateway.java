package com.mbs.qlcc.usecases.output.Workflow;

import com.mbs.qlcc.entities.Workflow.Workflow;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Workflow.WorkflowResponse;

public interface IWorkflowDsGateway {
    PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size);
    Workflow save(Workflow workflow);
}
