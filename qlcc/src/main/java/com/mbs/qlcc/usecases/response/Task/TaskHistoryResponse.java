package com.mbs.qlcc.usecases.response.Task;

import java.time.LocalDateTime;

public class TaskHistoryResponse {
    private String id;
    private String taskId;
    private String approverId;
    private String orgId;
    private Integer stepOrder;
    private String action; // 'APPROVED', 'REJECTED','PENDING'
    private String comment;

    public TaskHistoryResponse(String id, String taskId, String approverId, String orgId, Integer stepOrder, String action, String comment) {
        this.id = id;
        this.taskId = taskId;
        this.approverId = approverId;
        this.orgId = orgId;
        this.stepOrder = stepOrder;
        this.action = action;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
