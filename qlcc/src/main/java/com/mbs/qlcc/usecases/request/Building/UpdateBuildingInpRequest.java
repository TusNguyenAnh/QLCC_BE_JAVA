package com.mbs.qlcc.usecases.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UpdateBuildingInpRequest {
    private String buildingName;
    private Float financialRatio;

    public UpdateBuildingInpRequest() {
    }

    public UpdateBuildingInpRequest(String buildingName, Float financialRatio) {
        this.buildingName = buildingName;
        this.financialRatio = financialRatio;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Float getFinancialRatio() {
        return financialRatio;
    }

    public void setFinancialRatio(Float financialRatio) {
        this.financialRatio = financialRatio;
    }
}
