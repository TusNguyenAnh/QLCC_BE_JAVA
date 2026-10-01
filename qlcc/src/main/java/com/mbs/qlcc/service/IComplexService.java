package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Complex.ApproveRejectComplexRequest;
import com.mbs.qlcc.dto.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.dto.request.Complex.FilterComplexRequest;
import com.mbs.qlcc.dto.response.Complex.ComplexResponse;
import com.mbs.qlcc.dto.response.PageResponse;

import java.io.IOException;
import java.util.List;

public interface IComplexService {
    ComplexResponse create(CreateComplexRequest request) throws IOException;
    ComplexResponse findById(String id);
    PageResponse<ComplexResponse> filterByStatus(int status, FilterComplexRequest request);
    List<ComplexResponse> approveComplex(ApproveRejectComplexRequest request);
    void rejectComplex(ApproveRejectComplexRequest request);
}
