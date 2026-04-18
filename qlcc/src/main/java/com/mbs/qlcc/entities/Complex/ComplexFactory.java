package com.mbs.qlcc.entities.Complex;

/**
 * Factory Implementation for Creating Complex Entities
 */
public class ComplexFactory implements IComplexFactory {
    @Override
    public Complex create(String complexName, String address, int totalBuilding, int totalApartment,
                         String nameContact, String phoneContact, String emailContact,
                         String description, String financialModel) {
        return new Complex(complexName, address, totalBuilding, totalApartment,
                nameContact, phoneContact, emailContact, description, financialModel);
    }
}
