package com.mbs.qlcc.usecases.response.Organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

public class OrganizationResponse {
    String id;
    String orgCode;
    String orgName;
    String complexId;
    String parentOrgId;
    String description;
    String status;
    Integer level;
    List<OrganizationResponse> children;  // Recursive for hierarchical structure
    List<String> buildingIds;  // List of managed building IDs

    public OrganizationResponse() {
    }

    public OrganizationResponse(String id, String orgCode, String orgName) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
    }

    public OrganizationResponse(String id, String orgCode, String orgName, String complexId, String parentOrgId, String description, String status, Integer level) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.status = status;
        this.level = level;
    }

    public OrganizationResponse(String id, String orgCode, String orgName, String complexId, String parentOrgId, String description, String status, Integer level, List<OrganizationResponse> children, List<String> buildingIds) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.complexId = complexId;
        this.parentOrgId = parentOrgId;
        this.description = description;
        this.status = status;
        this.level = level;
        this.children = children;
        this.buildingIds = buildingIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<OrganizationResponse> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationResponse> children) {
        this.children = children;
    }

    public List<String> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }
}
