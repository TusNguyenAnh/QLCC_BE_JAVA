package com.mbs.qlcc.usecases.request.Workflow;

import java.util.List;

public class WorkflowInpRequest {
    private String complexId;
    private String workflowName;
    private String description;
    private List<WorkflowStepInpRequest> workflowSteps;

    public WorkflowInpRequest(String complexId, String workflowName, String description, List<WorkflowStepInpRequest> workflowSteps) {
        this.complexId = complexId;
        this.workflowName = workflowName;
        this.description = description;
        this.workflowSteps = workflowSteps;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WorkflowStepInpRequest> getWorkflowSteps() {
        return workflowSteps;
    }

    public void setWorkflowSteps(List<WorkflowStepInpRequest> workflowSteps) {
        this.workflowSteps = workflowSteps;
    }
}
