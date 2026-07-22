package com.mbs.qlcc.entities.Task;

import java.time.LocalDateTime;

public class TaskHistoryFactory implements ITaskHistoryFactory {
    @Override
    public TaskHistory createTaskHistory(String taskId, String approverId, String orgId, Integer stepOrder, String action, String comment) {
        return new TaskHistory(taskId, approverId, orgId, stepOrder, action, comment, LocalDateTime.now(), LocalDateTime.now(), null, false);
    }
}
