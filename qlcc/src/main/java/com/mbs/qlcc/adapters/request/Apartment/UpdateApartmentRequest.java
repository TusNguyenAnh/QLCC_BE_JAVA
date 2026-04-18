package com.mbs.qlcc.adapters.request.Apartment;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateApartmentRequest {
    private int floor;
    private String aptNumber;
    private Double grossArea;
    private Double coefficient;
    private String aptType;
    private String description;
}
