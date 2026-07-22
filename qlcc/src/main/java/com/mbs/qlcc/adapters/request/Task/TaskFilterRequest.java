package com.mbs.qlcc.adapters.request.Task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskFilterRequest {
    private List<String> priorityId;
    private List<String> taskTypeId;
    private LocalDateTime timeApprovedStart;
    private LocalDateTime timeApprovedEnd;
    private LocalDateTime timeRequestEnd;
    private LocalDateTime timeRequestStart;
    private String order;
    int pageNumber;
    int pageSize;
}
