package com.mbs.qlcc.dto.response.Task;

import lombok.Getter;

@Getter
public class TaskResponse {
    private String id;
    private String complexId;
    private String taskTypeId;
    private String currentOrgId;
    private String creator;
    private Integer currentStep;
    private String taskName;
    private String description;
    private String status;
    private String category;

    public TaskResponse(String id, String complexId, String taskTypeId, String currentOrgId, String creator, Integer currentStep, String taskName, String description, String status, String category) {
        this.id = id;
        this.complexId = complexId;
        this.taskTypeId = taskTypeId;
        this.currentOrgId = currentOrgId;
        this.creator = creator;
        this.currentStep = currentStep;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public void setCurrentOrgId(String currentOrgId) {
        this.currentOrgId = currentOrgId;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
