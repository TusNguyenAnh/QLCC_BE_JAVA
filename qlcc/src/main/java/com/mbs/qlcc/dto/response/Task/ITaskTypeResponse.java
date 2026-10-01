package com.mbs.qlcc.dto.response.Task;

import com.mbs.qlcc.dto.response.Priority.IPriorityResponse;

public interface ITaskTypeResponse {
    String getId();
    String getTypeName();
    String getDescription();
    Integer getStatus();
    String getComplexId();
    IPriorityResponse getPriority();
}
