package com.mbs.qlcc.usecases.response.Task;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getCurrentOrgId() {
        return currentOrgId;
    }

    public void setCurrentOrgId(String currentOrgId) {
        this.currentOrgId = currentOrgId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
