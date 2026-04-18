package com.mbs.qlcc.usecases.response.Apartment;

/**
 * ApartmentResponse - Output DTO cho Apartment
 * Không bao gồm nhạy cảm fields như status
 */
public class ApartmentResponse {
    private String id;
    private String buildingId;
    private String complexId;
    private int floor;
    private String aptNumber;
    private Double grossArea;
    private Double carpetArea;
    private Double coefficient;
    private String aptType;
    private String description;

    public ApartmentResponse() {
    }

    public ApartmentResponse(String id, String buildingId, String complexId, int floor,
                            String aptNumber, Double grossArea, Double carpetArea,
                            Double coefficient, String aptType, String description) {
        this.id = id;
        this.buildingId = buildingId;
        this.complexId = complexId;
        this.floor = floor;
        this.aptNumber = aptNumber;
        this.grossArea = grossArea;
        this.carpetArea = carpetArea;
        this.coefficient = coefficient;
        this.aptType = aptType;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Double carpetArea) {
        this.carpetArea = carpetArea;
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
