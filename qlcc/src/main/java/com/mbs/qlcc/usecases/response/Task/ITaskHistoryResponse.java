package com.mbs.qlcc.usecases.response.Task;

public interface ITaskHistoryResponse {
    String getId();

    String getTaskId();

    String getApproverId();

    String getOrgId();

    Integer getStepOrder();

    String getAction(); // 'APPROVED', 'REJECTED','PENDING'

    String getComment();

    String getRoleName();

    String getOrgName();

    Integer getLevel();

    String getWorkflowName();

    String getFullname();
}
