package com.mbs.qlcc.usecases.request.Apartment;

import java.math.BigDecimal;

public class UpdateApartmentInpRequest {
    private int floor;
    private String aptNumber;
    private BigDecimal grossArea;
    private BigDecimal coefficient;
    private String aptType;
    private String description;

    public UpdateApartmentInpRequest() {
    }

    public UpdateApartmentInpRequest(int floor, String aptNumber, BigDecimal grossArea,
                                     BigDecimal coefficient, String aptType, String description) {
        this.floor = floor;
        this.aptNumber = aptNumber;
        this.grossArea = grossArea;
        this.coefficient = coefficient;
        this.aptType = aptType;
        this.description = description;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public BigDecimal getGrossArea() {
        return grossArea;
    }

    public void setGrossArea(BigDecimal grossArea) {
        this.grossArea = grossArea;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    public String getAptType() {
        return aptType;
    }

    public void setAptType(String aptType) {
        this.aptType = aptType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
