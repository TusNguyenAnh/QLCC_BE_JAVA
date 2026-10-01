package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Apartment.CreateApartmentRequest;
import com.mbs.qlcc.dto.request.Apartment.FilterApartmentRequest;
import com.mbs.qlcc.dto.request.Apartment.UpdateApartmentRequest;
import com.mbs.qlcc.dto.response.Apartment.ApartmentResponse;
import com.mbs.qlcc.dto.response.PageResponse;

import java.util.List;

public interface IApartmentService {

    ApartmentResponse create(CreateApartmentRequest request, String complexId);

    ApartmentResponse findById(String id);

    PageResponse<ApartmentResponse> findByBuildingId(String buildingId, int pageNumber, int pageSize);

    PageResponse<ApartmentResponse> filterByStatus(int status, FilterApartmentRequest request);

    PageResponse<ApartmentResponse> findByComplexId(String complexId, int pageNumber, int pageSize);

    ApartmentResponse update(String id, UpdateApartmentRequest request);

    String importExcel(List<CreateApartmentRequest> request, String complexId);
}
