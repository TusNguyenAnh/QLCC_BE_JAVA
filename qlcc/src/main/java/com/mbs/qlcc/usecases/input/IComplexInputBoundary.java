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

    /**
     * Create a new complex
     */
    ComplexResponse create(CreateComplexInpRequest request);

    /**
     * Find complex by ID
     */
    ComplexResponse findById(String id);

    /**
     * Filter complexes by status with pagination
     */
    PageResponse<ComplexResponse> filterByStatus(int status, FilterComplexInpRequest request);

    /**
     * Approve complexes
     */
    List<ComplexResponse> approveComplex(ApproveRejectComplexInpRequest request);

    /**
     * Reject complexes
     */
    void rejectComplex(ApproveRejectComplexInpRequest request);
}

