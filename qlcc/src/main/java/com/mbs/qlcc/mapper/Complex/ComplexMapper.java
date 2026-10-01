package com.mbs.qlcc.mapper.Complex;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.dto.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.dto.response.Complex.ComplexResponse;

public final class ComplexMapper {
    private ComplexMapper() {
    }

    public static Complex toEntity(CreateComplexRequest request) {
        return Complex.builder()
                .complexName(request.getComplexName())
                .address(request.getAddress())
                .totalBuilding(request.getTotalBuilding())
                .totalApartment(request.getTotalApartment())
                .nameContact(request.getNameContact())
                .phoneContact(request.getPhoneContact())
                .emailContact(request.getEmailContact())
                .description(request.getDescription())
                .financialModel(request.getFinancialModel())
                .build();
    }

    public static ComplexResponse toResponse(Complex complex) {
        return new ComplexResponse(
                complex.getId(),
                complex.getComplexName(),
                complex.getAddress(),
                complex.getTotalBuilding(),
                complex.getTotalApartment(),
                complex.getNameContact(),
                complex.getPhoneContact(),
                complex.getEmailContact(),
                complex.getDescription(),
                complex.getFinancialModel()
        );
    }
}
