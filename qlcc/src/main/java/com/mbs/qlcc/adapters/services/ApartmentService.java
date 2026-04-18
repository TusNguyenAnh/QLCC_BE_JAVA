package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Apartment.CreateApartmentRequest;
import com.mbs.qlcc.adapters.request.Apartment.FilterApartmentRequest;
import com.mbs.qlcc.adapters.request.Apartment.UpdateApartmentRequest;
import com.mbs.qlcc.usecases.input.IApartmentInputBoundary;
import com.mbs.qlcc.usecases.request.Apartment.CreateApartmentInpRequest;
import com.mbs.qlcc.usecases.request.Apartment.FilterApartmentInpRequest;
import com.mbs.qlcc.usecases.request.Apartment.UpdateApartmentInpRequest;
import com.mbs.qlcc.usecases.response.Apartment.ApartmentResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final IApartmentInputBoundary apartmentUseCase;

    @Transactional
    public ApartmentResponse create(CreateApartmentRequest request, String complexId) {
        // Transform: CreateApartmentRequest → CreateApartmentInpRequest
        CreateApartmentInpRequest inpRequest = new CreateApartmentInpRequest(
                request.getBuildingId(),
                complexId,
                request.getFloor(),
                request.getAptNumber(),
                request.getGrossArea(),
                request.getCoefficient(),
                request.getAptType(),
                request.getDescription()
        );

        return apartmentUseCase.create(inpRequest);
    }

    public ApartmentResponse findById(String id) {
        return apartmentUseCase.findById(id);
    }


    public PageResponse<ApartmentResponse> findByBuildingId(String buildingId, int pageNumber, int pageSize) {
        return apartmentUseCase.findByBuildingId(buildingId, pageNumber, pageSize);
    }

    public PageResponse<ApartmentResponse> filterByStatus(int status, FilterApartmentRequest request) {
        FilterApartmentInpRequest filterInput = new FilterApartmentInpRequest(
                request.getKeyword(),
                request.getTimeRequestStart(),
                request.getTimeRequestEnd(),
                request.getOrder(),
                request.getPageNumber(),
                request.getPageSize()
        );

        return apartmentUseCase.filterByStatus(status, filterInput);
    }


    public PageResponse<ApartmentResponse> findByComplexId(String complexId, int pageNumber, int pageSize) {
        return apartmentUseCase.findByComplexId(complexId, pageNumber, pageSize);
    }


    @Transactional
    public ApartmentResponse update(String id, UpdateApartmentRequest request) {
        // Transform to UseCase request
        UpdateApartmentInpRequest inpRequest = new UpdateApartmentInpRequest(
                request.getFloor(),
                request.getAptNumber(),
                request.getGrossArea(),
                request.getCoefficient(),
                request.getAptType(),
                request.getDescription()
        );

        return apartmentUseCase.update(id, inpRequest);
    }

    @Transactional
    public String importExcel(List<CreateApartmentRequest> request, String complexId) {
        List<CreateApartmentInpRequest> createApartmentInpRequests = new ArrayList<>();
        for (CreateApartmentRequest crq : request
        ) {
            CreateApartmentInpRequest inpRequest = new CreateApartmentInpRequest(
                    crq.getBuildingId(),
                    complexId,
                    crq.getFloor(),
                    crq.getAptNumber(),
                    crq.getGrossArea(),
                    crq.getCoefficient(),
                    crq.getAptType(),
                    crq.getDescription()
            );
            createApartmentInpRequests.add(inpRequest);
        }

        return apartmentUseCase.importExcel(createApartmentInpRequests,complexId);
    }

}
