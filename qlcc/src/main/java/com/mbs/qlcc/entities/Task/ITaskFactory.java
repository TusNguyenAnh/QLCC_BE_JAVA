package com.mbs.qlcc.entities.Task;

public interface ITaskFactory {
    Task createTask(String complexId, String taskTypeId, String currentOrgId, String creator, Integer currentStep, String taskName, String description, String status, String category);
}
