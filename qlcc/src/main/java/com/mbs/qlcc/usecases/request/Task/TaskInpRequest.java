package com.mbs.qlcc.usecases.request.Task;

import java.util.List;

public class TaskInpRequest {
    private String tasktypeId;
    private List<String> buildingId;
    private String taskName;
    private String description;
    private String category;
    private String creator;
    private String complexId;

    public TaskInpRequest(String tasktypeId, List<String> buildingId, String taskName, String description, String category, String creator, String complexId) {
        this.tasktypeId = tasktypeId;
        this.buildingId = buildingId;
        this.taskName = taskName;
        this.description = description;
        this.category = category;
        this.creator = creator;
        this.complexId = complexId;
    }

    public String getTasktypeId() {
        return tasktypeId;
    }

    public void setTasktypeId(String tasktypeId) {
        this.tasktypeId = tasktypeId;
    }

    public List<String> getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(List<String> buildingId) {
        this.buildingId = buildingId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }
}
