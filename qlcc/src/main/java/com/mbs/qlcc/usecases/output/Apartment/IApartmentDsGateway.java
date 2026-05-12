package com.mbs.qlcc.usecases.output.Apartment;

import com.mbs.qlcc.entities.Apartment.Apartment;
import com.mbs.qlcc.usecases.request.Apartment.FilterApartmentInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;

import java.util.List;
import java.util.Optional;

public interface IApartmentDsGateway {

    // Existence checks
    boolean existsByBuildingIdAndAptNumber(String buildingId, String aptNumber);

    List<Apartment> findByBuildingIdAndAptNumberIn(String buildingId, List<String> aptNumber);

    List<Apartment> findByBuildingIdIn(List<String> buildingId);

    // Find by ID
    Optional<Apartment> findById(String id);

    // Find by building
    PageResponse<Apartment> findByBuildingId(String buildingId, int pageNumber, int pageSize);

    // Find by status and filters
    PageResponse<Apartment> findByStatus(int status, FilterApartmentInpRequest filter);

    // Find all by complex
    PageResponse<Apartment> findByComplexId(String complexId, int pageNumber, int pageSize);

    // Find batch
    List<Apartment> findAllByStatusAndIds(int status, List<String> ids);

    // Save single
    Apartment save(Apartment apartment);

    void importExcel(List<Apartment> apartments);

    // Update
    Apartment update(String id, Apartment apartment);

    // Batch update status
    void updateStatusBatch(List<String> ids, int newStatus);

    // Soft delete
    void softDelete(String id);

    // Batch soft delete
    void softDeleteBatch(List<String> ids);


    // Check duplicate apt_number in building (excluding specific apartment)
    boolean existsDuplicateAptNumber(String buildingId, String aptNumber, String excludeId);
}
