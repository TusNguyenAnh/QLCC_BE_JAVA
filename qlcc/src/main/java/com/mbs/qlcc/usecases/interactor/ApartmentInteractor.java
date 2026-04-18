package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Apartment.Apartment;
import com.mbs.qlcc.entities.Apartment.IApartmentFactory;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IApartmentInputBoundary;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.request.Apartment.FilterApartmentInpRequest;
import com.mbs.qlcc.usecases.output.Apartment.IApartmentDsGateway;
import com.mbs.qlcc.usecases.request.Apartment.CreateApartmentInpRequest;
import com.mbs.qlcc.usecases.request.Apartment.UpdateApartmentInpRequest;
import com.mbs.qlcc.usecases.response.Apartment.ApartmentResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.ErrorCode;

import java.util.*;
import java.util.stream.Collectors;

public class ApartmentInteractor implements IApartmentInputBoundary {
    IApartmentDsGateway apartmentGateway;
    IBuildingDsGateway buildingDsGateway;
    IApartmentFactory apartmentFactory;

    public ApartmentInteractor(IApartmentDsGateway apartmentGateway, IBuildingDsGateway buildingDsGateway, IApartmentFactory apartmentFactory) {
        this.apartmentGateway = apartmentGateway;
        this.buildingDsGateway = buildingDsGateway;
        this.apartmentFactory = apartmentFactory;
    }

    @Override
    public ApartmentResponse create(CreateApartmentInpRequest request) {
        // Validate: apartNumber unique per building
        boolean isExited = apartmentGateway.existsByBuildingIdAndAptNumber(request.getBuildingId(), request.getAptNumber());
        if (isExited) {
            throw new AppException(ErrorCode.APT_NUMBER_EXISTED);
        }

        // Create entity
        Apartment apartment = apartmentFactory.create(
                request.getBuildingId(),
                request.getComplexId(),
                request.getFloor(),
                request.getAptNumber(),
                request.getGrossArea(),
                request.getCoefficient(),
                request.getAptType(),
                request.getDescription()
        );

        // Save
        Apartment saved = apartmentGateway.save(apartment);

        return mapToResponse(saved);
    }

    @Override
    public String importExcel(List<CreateApartmentInpRequest> request, String complexId) {
        Set<String> uniqueBuilding = request
                .stream()
                .map(CreateApartmentInpRequest::getBuildingId)
                .collect(Collectors.toSet());

        Map<String, String> exitsBuilding = buildingDsGateway.findByBuildingName(uniqueBuilding, complexId);

        Set<String> result = new HashSet<>(uniqueBuilding);
        result.removeAll(exitsBuilding.keySet());

        if (!result.isEmpty()) {
            String error = "";
            for (String bdName : result
            ) {
                error = error + bdName + ", ";
            }
            error = error + "không tồn tại!";
            return error;
        }

        List<Apartment> apartments = new ArrayList<>();

        for (CreateApartmentInpRequest crq : request
        ) {
            Apartment apartment = apartmentFactory.create(
                    exitsBuilding.get(crq.getBuildingId()),
                    crq.getComplexId(),
                    crq.getFloor(),
                    crq.getAptNumber(),
                    crq.getGrossArea(),
                    crq.getCoefficient(),
                    crq.getAptType(),
                    crq.getDescription()
            );
            apartments.add(apartment);
        }

        apartmentGateway.importExcel(apartments);
        return "DONE";
    }

    @Override
    public ApartmentResponse findById(String id) {
        Optional<Apartment> apartment = apartmentGateway.findById(id);
        if (apartment.isEmpty()) {
            throw new AppException(ErrorCode.APARTMENT_NOT_FOUND);
        }

        return mapToResponse(apartment.get());
    }

    @Override
    public PageResponse<ApartmentResponse> findByBuildingId(String buildingId, int pageNumber, int pageSize) {
        PageResponse<Apartment> pageResponse = apartmentGateway.findByBuildingId(buildingId, pageNumber, pageSize);

        return new PageResponse<>(
                pageResponse.getData().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageResponse.getPage(),
                pageResponse.getSize(),
                pageResponse.getTotalElements(),
                pageResponse.getTotalPages()
        );
    }

    @Override
    public PageResponse<ApartmentResponse> filterByStatus(int status, FilterApartmentInpRequest filter) {
        PageResponse<Apartment> pageResponse = apartmentGateway.findByStatus(status, filter);

        return new PageResponse<>(
                pageResponse.getData().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageResponse.getPage(),
                pageResponse.getSize(),
                pageResponse.getTotalElements(),
                pageResponse.getTotalPages()
        );
    }

    @Override
    public PageResponse<ApartmentResponse> findByComplexId(String complexId, int pageNumber, int pageSize) {
        PageResponse<Apartment> pageResponse = apartmentGateway.findByComplexId(complexId, pageNumber, pageSize);

        return new PageResponse<>(
                pageResponse.getData().stream().map(this::mapToResponse).collect(Collectors.toList()),
                pageResponse.getPage(),
                pageResponse.getSize(),
                pageResponse.getTotalElements(),
                pageResponse.getTotalPages()
        );
    }

    @Override
    public ApartmentResponse update(String id, UpdateApartmentInpRequest request) {
        // Check apartment exists
        Optional<Apartment> existing = apartmentGateway.findById(id);
        if (existing.isEmpty()) {
            throw new AppException(ErrorCode.APARTMENT_NOT_FOUND);
        }

        Apartment apartment = existing.get();

        // Validate: aptNumber unique per building (excluding current apartment)
        if (!apartment.getAptNumber().equals(request.getAptNumber())) {
            if (apartmentGateway.existsByBuildingIdAndAptNumber(apartment.getBuildingId(), request.getAptNumber())) {
                throw new AppException(ErrorCode.APT_NUMBER_EXISTED);
            }
        }

        // Calculate carpet_area
        Double carpetArea = request.getGrossArea() * request.getCoefficient();

        // Update fields
        apartment.setFloor(request.getFloor());
        apartment.setAptNumber(request.getAptNumber());
        apartment.setGrossArea(request.getGrossArea());
        apartment.setCarpetArea(carpetArea);
        apartment.setCoefficient(request.getCoefficient());
        apartment.setAptType(request.getAptType());
        apartment.setDescription(request.getDescription());

        // Save
        Apartment updated = apartmentGateway.update(id, apartment);

        return mapToResponse(updated);
    }

    // Mapping helper
    private ApartmentResponse mapToResponse(Apartment apartment) {
        return new ApartmentResponse(
                apartment.getId(),
                apartment.getBuildingId(),
                apartment.getComplexId(),
                apartment.getFloor(),
                apartment.getAptNumber(),
                apartment.getGrossArea(),
                apartment.getCarpetArea(),
                apartment.getCoefficient(),
                apartment.getAptType(),
                apartment.getDescription()
        );
    }
}
