package com.mbs.qlcc.service;

import com.mbs.qlcc.domain.Brief;
import com.mbs.qlcc.dto.request.Brief.CreateBriefRequest;
import com.mbs.qlcc.dto.request.Brief.UpdateBriefRequest;
import com.mbs.qlcc.dto.response.Brief.BriefResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IBriefService {

    BriefResponse create(CreateBriefRequest request, String complexId) throws IOException;

    BriefResponse update(String id, UpdateBriefRequest request, String complexId);

    BriefResponse findById(String id, String complexId);

    PageResponse<BriefResponse> filter(String complexId, String briefName, String type, Pageable pageable);

    void delete(String id, String complexId);

}
