package com.mbs.qlcc.usecases.request.Task;

public class TaskUpdateInpRequest {
    private String action;
    private String comment;
    private String approverId;
    private String complexId;

    public TaskUpdateInpRequest(String action, String comment, String approverId, String complexId) {
        this.action = action;
        this.comment = comment;
        this.approverId = approverId;
        this.complexId = complexId;
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

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }
}
