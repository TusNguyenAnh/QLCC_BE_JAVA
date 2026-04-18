package com.mbs.qlcc.usecases.request.Organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


public class CreateOrganizationInpRequest {
    String orgCode;
    String orgName;
    String complexId;
    String parentOrgId;      // Optional, null for root org
    String description;
    List<String> buildingIds;  // Building IDs to manage

    public CreateOrganizationInpRequest() {
    }

    public CreateOrganizationInpRequest(String orgCode, String orgName, String complexId, String parentOrgId, String description, List<String> buildingIds) {
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.buildingIds = buildingIds;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }
}
