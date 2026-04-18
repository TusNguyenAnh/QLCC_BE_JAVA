package com.mbs.qlcc.entities.Apartment;

public interface IApartmentFactory {
    Apartment create(String buildingId, String complexId, int floor,
                     String aptNumber, Double grossArea, Double coefficient,
                     String aptType, String description);
}
