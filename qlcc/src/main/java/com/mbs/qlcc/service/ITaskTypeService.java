package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.TaskType.TaskTypeRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Task.ITaskTypeResponse;
import com.mbs.qlcc.dto.response.Task.TaskTypeResponse;

public interface ITaskTypeService {
    TaskTypeResponse create(TaskTypeRequest request);

    PageResponse<ITaskTypeResponse> getAllTaskType(int page, int perPage);
}
