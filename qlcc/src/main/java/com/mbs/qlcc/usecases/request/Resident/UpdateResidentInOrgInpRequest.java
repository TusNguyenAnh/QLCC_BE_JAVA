package com.mbs.qlcc.usecases.request.Resident;

import java.util.List;


public class UpdateResidentInOrgInpRequest {
    private String orgId;
    private List<String> userIds;

    public UpdateResidentInOrgInpRequest() {}

    public UpdateResidentInOrgInpRequest(String orgId, List<String> userIds) {
        this.orgId = orgId;
        this.userIds = userIds;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
