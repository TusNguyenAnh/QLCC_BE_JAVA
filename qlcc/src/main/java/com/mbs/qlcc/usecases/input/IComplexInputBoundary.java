package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Complex.ApproveRejectComplexInpRequest;
import com.mbs.qlcc.usecases.request.Complex.CreateComplexInpRequest;
import com.mbs.qlcc.usecases.request.Complex.FilterComplexInpRequest;
import com.mbs.qlcc.usecases.response.Complex.ComplexResponse;
import com.mbs.qlcc.usecases.response.PageResponse;

import java.util.List;

/**
 * Complex Input Boundary Interface
 * Defines use case operations for Complex
 */
public interface IComplexInputBoundary {
    ComplexResponse create(CreateComplexInpRequest request);


    ComplexResponse findById(String id);


    PageResponse<ComplexResponse> filterByStatus(int status, FilterComplexInpRequest request);


    List<ComplexResponse> approveComplex(ApproveRejectComplexInpRequest request);


    void rejectComplex(ApproveRejectComplexInpRequest request);
}

