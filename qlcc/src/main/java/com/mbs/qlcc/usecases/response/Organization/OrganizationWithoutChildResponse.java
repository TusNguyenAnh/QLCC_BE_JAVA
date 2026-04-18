package com.mbs.qlcc.usecases.response.Organization;

import java.util.List;

public class OrganizationWithoutChildResponse {
    String id;
    String orgCode;
    String orgName;

    public OrganizationWithoutChildResponse() {
    }

    public OrganizationWithoutChildResponse(String id, String orgCode, String orgName) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
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


}
