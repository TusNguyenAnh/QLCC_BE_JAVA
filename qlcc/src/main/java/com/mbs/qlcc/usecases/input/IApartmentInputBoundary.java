package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Apartment.CreateApartmentInpRequest;
import com.mbs.qlcc.usecases.request.Apartment.UpdateApartmentInpRequest;
import com.mbs.qlcc.usecases.response.Apartment.ApartmentResponse;
import com.mbs.qlcc.usecases.request.Apartment.FilterApartmentInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;

import java.util.List;

public interface IApartmentInputBoundary {
    ApartmentResponse create(CreateApartmentInpRequest request);
    String importExcel(List<CreateApartmentInpRequest> request,String complexId);

    ApartmentResponse findById(String id);

    PageResponse<ApartmentResponse> findByBuildingId(String buildingId, int pageNumber, int pageSize);

    PageResponse<ApartmentResponse> filterByStatus(int status, FilterApartmentInpRequest filter);

    PageResponse<ApartmentResponse> findByComplexId(String complexId, int pageNumber, int pageSize);

    ApartmentResponse update(String id, UpdateApartmentInpRequest request);

}
