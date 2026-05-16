package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Complex.ApproveRejectComplexRequest;
import com.mbs.qlcc.adapters.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.adapters.request.Complex.FilterComplexRequest;
import com.mbs.qlcc.usecases.input.IComplexInputBoundary;
import com.mbs.qlcc.usecases.request.Complex.ApproveRejectComplexInpRequest;
import com.mbs.qlcc.usecases.request.Complex.CreateComplexInpRequest;
import com.mbs.qlcc.usecases.request.Complex.FilterComplexInpRequest;
import com.mbs.qlcc.usecases.response.Complex.ComplexResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComplexService {

    IComplexInputBoundary useCase;
    MediaFileService mediaFileService;


    @Transactional
    public ComplexResponse create(CreateComplexRequest request) throws IOException {
        CreateComplexInpRequest inpRequest = new CreateComplexInpRequest(
                request.getComplexName(),
                request.getAddress(),
                request.getTotalBuilding(),
                request.getTotalApartment(),
                request.getNameContact(),
                request.getPhoneContact(),
                request.getEmailContact(),
                request.getDescription(),
                "");
        ComplexResponse complexResponse = useCase.create(inpRequest);
        mediaFileService.create(request.getFiles(), "complex", complexResponse.getId());
        return complexResponse;
    }

    public ComplexResponse findById(String id) {
        return useCase.findById(id);
    }

    @PreAuthorize("hasAuthority('manage:complex')")
    public PageResponse<ComplexResponse> filterByStatus(int status, FilterComplexRequest request) {
        FilterComplexInpRequest inpRequest = new FilterComplexInpRequest(
                request.getKeyword(),
                request.getTimeRequestStart(),
                request.getTimeRequestEnd(),
                request.getOrder(),
                request.getPageNumber(),
                request.getPageSize());

        return useCase.filterByStatus(status, inpRequest);
    }

    /**
     * Approve complexes
     */
    @Transactional
    public List<ComplexResponse> approveComplex(ApproveRejectComplexRequest request) {
        ApproveRejectComplexInpRequest inpRequest = new ApproveRejectComplexInpRequest(request.getIds());

        return useCase.approveComplex(inpRequest);
    }

    /**
     * Reject complexes
     */
    @Transactional
    public void rejectComplex(ApproveRejectComplexRequest request) {
        ApproveRejectComplexInpRequest inpRequest = new ApproveRejectComplexInpRequest(request.getIds());
        useCase.rejectComplex(inpRequest);
    }
}
