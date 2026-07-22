package com.mbs.qlcc.entities.Task;

import java.time.LocalDateTime;

public class TaskFactory implements ITaskFactory {
    @Override
    public Task createTask(String complexId, String taskTypeId, String currentOrgId, String creator, Integer currentStep, String taskName, String description, String status, String category) {
        return new Task(complexId, taskTypeId, currentOrgId, creator, currentStep, taskName, description, status, category, LocalDateTime.now(), LocalDateTime.now(), null, false);
    }
}
