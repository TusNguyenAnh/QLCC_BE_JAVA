package com.mbs.qlcc.entities.Task;

public interface ITaskHistoryFactory {
    TaskHistory createTaskHistory(String taskId, String approverId, String orgId, Integer stepOrder, String action, String comment);
}
