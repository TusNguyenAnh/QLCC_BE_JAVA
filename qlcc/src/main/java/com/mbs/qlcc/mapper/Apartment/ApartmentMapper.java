package com.mbs.qlcc.mapper.Apartment;

import com.mbs.qlcc.domain.Apartment;
import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.dto.request.Apartment.CreateApartmentRequest;
import com.mbs.qlcc.dto.response.Apartment.ApartmentResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApartmentMapper {

    public static ApartmentResponse toResponse(Apartment apartment) {
        if (apartment == null) return null;

        String buildingId = apartment.getBuilding() != null ? apartment.getBuilding().getId() : null;

        return new ApartmentResponse(
                apartment.getId(),
                buildingId,
                apartment.getComplexId(),
                apartment.getFloor(),
                apartment.getAptNumber(),
                apartment.getGrossArea(),
                apartment.getCarpetArea(),
                apartment.getCoefficient(),
                apartment.getAptType(),
                apartment.getDescription(),
                apartment.getStatus()
        );
    }

    public static Apartment toEntity(CreateApartmentRequest request, String complexId, Building building) {
        if (request == null) return null;

        BigDecimal carpetArea = (request.getGrossArea() != null && request.getCoefficient() != null)
                ? request.getGrossArea().multiply(request.getCoefficient())
                : BigDecimal.ZERO;

        return Apartment.builder()
                .building(building)
                .complexId(complexId)
                .floor(request.getFloor())
                .aptNumber(request.getAptNumber())
                .grossArea(request.getGrossArea())
                .carpetArea(carpetArea)
                .coefficient(request.getCoefficient())
                .aptType(request.getAptType())
                .description(request.getDescription())
                .status(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private ApartmentMapper() {}
}
