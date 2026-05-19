package com.mbs.qlcc.adapters.request.Workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowRequest {
    private String workflowName;
    private String description;
    private List<WorkflowStepRequest> workflowSteps;
}
