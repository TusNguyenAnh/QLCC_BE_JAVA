package com.mbs.qlcc.usecases.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CreateBuildingInpRequest {
    private String complexId;
    private String buildingName;
    private Float financialRatio;

    public CreateBuildingInpRequest() {
    }

    public CreateBuildingInpRequest(String complexId, String buildingName, Float financialRatio) {
        this.complexId = complexId;
        this.buildingName = buildingName;
        this.financialRatio = financialRatio;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
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
