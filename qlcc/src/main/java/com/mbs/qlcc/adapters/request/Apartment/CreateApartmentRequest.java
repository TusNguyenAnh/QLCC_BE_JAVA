package com.mbs.qlcc.adapters.request.Apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateApartmentRequest {
    private String buildingId;
    private int floor;
    private String aptNumber;
    private BigDecimal grossArea;
    private BigDecimal coefficient;
    private String aptType;
    private String description;
}
