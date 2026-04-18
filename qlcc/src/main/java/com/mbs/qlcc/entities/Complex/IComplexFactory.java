package com.mbs.qlcc.entities.Complex;

/**
 * Factory Interface for Creating Complex Entities
 */
public interface IComplexFactory {
    /**
     * Create a new Complex entity
     * Status defaults to 0 (pending)
     */
    Complex create(String complexName, String address, int totalBuilding, int totalApartment,
                   String nameContact, String phoneContact, String emailContact,
                   String description, String financialModel);
}

