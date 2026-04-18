package com.mbs.qlcc.usecases.request.Organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


public class UpdateOrganizationInpRequest {
    String complexId;
    String orgCode;
    String orgName;
    String description;
    String parentOrgId;
    List<String> buildingIds;  // Building IDs to manage

    public UpdateOrganizationInpRequest() {
    }

    public UpdateOrganizationInpRequest(String complexId,String orgCode, String orgName, String description, String parentOrgId, List<String> buildingIds) {
        this.complexId = complexId;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.description = description;
        this.parentOrgId = parentOrgId;
        this.buildingIds = buildingIds;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public List<String> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }
}
