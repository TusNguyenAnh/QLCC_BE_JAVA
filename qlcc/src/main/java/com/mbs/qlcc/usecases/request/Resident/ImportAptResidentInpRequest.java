package com.mbs.qlcc.usecases.request.Resident;

import java.util.List;


public class ImportAptResidentInpRequest {
    private String buildingName;
    private String apartmentNumber;
    private String cccd;

    public ImportAptResidentInpRequest(String buildingName, String apartmentNumber, String cccd) {
        this.buildingName = buildingName;
        this.apartmentNumber = apartmentNumber;
        this.cccd = cccd;
    }


    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}
