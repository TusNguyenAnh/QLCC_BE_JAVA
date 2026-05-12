package com.mbs.qlcc.adapters.request.Apartment;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateApartmentRequest {
    private int floor;
    private String aptNumber;
    private BigDecimal grossArea;
    private BigDecimal coefficient;
    private String aptType;
    private String description;
}
