package com.mbs.qlcc.usecases.request.Apartment;

import java.math.BigDecimal;

public class CreateApartmentInpRequest {
    private String buildingId;
    private String complexId;
    private int floor;
    private String aptNumber;
    private BigDecimal grossArea;
    private BigDecimal coefficient;
    private String aptType;
    private String description;

    public CreateApartmentInpRequest() {
    }

    public CreateApartmentInpRequest(String buildingId, String complexId, int floor,
                                     String aptNumber, BigDecimal grossArea, BigDecimal coefficient,
                                     String aptType, String description) {
        this.buildingId = buildingId;
        this.complexId = complexId;
        this.floor = floor;
        this.aptNumber = aptNumber;
        this.grossArea = grossArea;
        this.coefficient = coefficient;
        this.aptType = aptType;
        this.description = description;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
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
