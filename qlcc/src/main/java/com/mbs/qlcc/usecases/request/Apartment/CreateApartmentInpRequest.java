package com.mbs.qlcc.usecases.request.Apartment;

public class CreateApartmentInpRequest {
    private String buildingId;
    private String complexId;
    private int floor;
    private String aptNumber;
    private Double grossArea;
    private Double coefficient;
    private String aptType;
    private String description;

    public CreateApartmentInpRequest() {
    }

    public CreateApartmentInpRequest(String buildingId, String complexId, int floor,
                                     String aptNumber, Double grossArea, Double coefficient,
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

    public Double getGrossArea() {
        return grossArea;
    }

    public void setGrossArea(Double grossArea) {
        this.grossArea = grossArea;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
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
