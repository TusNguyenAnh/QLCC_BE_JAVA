package com.mbs.qlcc.usecases.request.Task;

import java.time.LocalDateTime;
import java.util.List;

public class TaskFilterInpRequest {
    private List<String> priorityId;
    private List<String> taskTypeId;
    private LocalDateTime timeApprovedStart;
    private LocalDateTime timeApprovedEnd;
    private LocalDateTime timeRequestEnd;
    private LocalDateTime timeRequestStart;
    private String order;

    public TaskFilterInpRequest(List<String> priorityId, List<String> taskTypeId, LocalDateTime timeApprovedStart, LocalDateTime timeApprovedEnd, LocalDateTime timeRequestEnd, LocalDateTime timeRequestStart, String order) {
        this.priorityId = priorityId;
        this.taskTypeId = taskTypeId;
        this.timeApprovedStart = timeApprovedStart;
        this.timeApprovedEnd = timeApprovedEnd;
        this.timeRequestEnd = timeRequestEnd;
        this.timeRequestStart = timeRequestStart;
        this.order = order;
    }

    public List<String> getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(List<String> priorityId) {
        this.priorityId = priorityId;
    }

    public List<String> getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(List<String> taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public LocalDateTime getTimeApprovedStart() {
        return timeApprovedStart;
    }

    public void setTimeApprovedStart(LocalDateTime timeApprovedStart) {
        this.timeApprovedStart = timeApprovedStart;
    }

    public LocalDateTime getTimeApprovedEnd() {
        return timeApprovedEnd;
    }

    public void setTimeApprovedEnd(LocalDateTime timeApprovedEnd) {
        this.timeApprovedEnd = timeApprovedEnd;
    }

    public LocalDateTime getTimeRequestEnd() {
        return timeRequestEnd;
    }

    public void setTimeRequestEnd(LocalDateTime timeRequestEnd) {
        this.timeRequestEnd = timeRequestEnd;
    }

    public LocalDateTime getTimeRequestStart() {
        return timeRequestStart;
    }

    public void setTimeRequestStart(LocalDateTime timeRequestStart) {
        this.timeRequestStart = timeRequestStart;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
