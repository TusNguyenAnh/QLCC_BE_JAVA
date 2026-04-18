package com.mbs.qlcc.entities.Apartment;

import java.time.LocalDateTime;

public class ApartmentFactory implements IApartmentFactory {
    
    @Override
    public Apartment create(String buildingId, String complexId, int floor,
                           String aptNumber, Double grossArea, Double coefficient,
                           String aptType, String description) {
        
        // Tính carpet_area = gross_area * coefficient
        Double carpetArea = grossArea * coefficient;
        
        Apartment apartment = new Apartment();
        apartment.setBuildingId(buildingId);
        apartment.setComplexId(complexId);
        apartment.setFloor(floor);
        apartment.setAptNumber(aptNumber);
        apartment.setGrossArea(grossArea);
        apartment.setCarpetArea(carpetArea);
        apartment.setCoefficient(coefficient);
        apartment.setAptType(aptType);
        apartment.setDescription(description);
        apartment.setStatus(0); // Default: inactive
        apartment.setCreatedAt(LocalDateTime.now());
        apartment.setUpdatedAt(LocalDateTime.now());
        apartment.setDeletedAt(null);
        
        return apartment;
    }
}
