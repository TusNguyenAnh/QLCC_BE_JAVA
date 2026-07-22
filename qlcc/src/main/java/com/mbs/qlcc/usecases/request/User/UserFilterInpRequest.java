package com.mbs.qlcc.usecases.request.User;

public class UserFilterInpRequest {
    private String buildingId;
    private int floor;
    String aptNumber;
    String relationship;

    public UserFilterInpRequest(String buildingId, int floor, String aptNumber, String relationship) {
        this.buildingId = buildingId;
        this.floor = floor;
        this.aptNumber = aptNumber;
        this.relationship = relationship;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
