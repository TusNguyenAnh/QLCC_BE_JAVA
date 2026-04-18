package com.mbs.qlcc.adapters.request.Apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateApartmentRequest {
    private String buildingId;
    private int floor;
    private String aptNumber;
    private Double grossArea;
    private Double coefficient;
    private String aptType;
    private String description;
}
