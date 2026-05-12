package com.mbs.qlcc.usecases.request.Resident;

public class FilterResidentInpRequest {
    private int floor;
    private String buildingId;
    private String aptNumber;
    private String relationship;
    private String order;
    private int pageNumber;
    private int pageSize;

    public FilterResidentInpRequest() {}

    public FilterResidentInpRequest(int floor, String buildingId, String aptNumber, String relationship, String order, int pageNumber, int pageSize) {
        this.floor = floor;
        this.buildingId = buildingId;
        this.aptNumber = aptNumber;
        this.relationship = relationship;
        this.order = order;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
