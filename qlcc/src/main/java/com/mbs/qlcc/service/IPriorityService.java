package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Priority.PriorityResponse;

public interface IPriorityService {
    PageResponse<PriorityResponse> show(int page, int perPage);
}
