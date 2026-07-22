package com.mbs.qlcc.usecases.response.Task;

import com.mbs.qlcc.usecases.response.Priority.IPriorityResponse;

public interface ITaskTypeResponse {
    String getId();
    String getTypeName();
    String getDescription();
    Integer getStatus();
    String getComplexId();
    IPriorityResponse getPriority();
}
