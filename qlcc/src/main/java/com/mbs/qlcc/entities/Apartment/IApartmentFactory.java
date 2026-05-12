package com.mbs.qlcc.entities.Apartment;

import java.math.BigDecimal;

public interface IApartmentFactory {
    Apartment create(String buildingId, String complexId, int floor,
                     String aptNumber, BigDecimal grossArea, BigDecimal coefficient,
                     String aptType, String description);
}
