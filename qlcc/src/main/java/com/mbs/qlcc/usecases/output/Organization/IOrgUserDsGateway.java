package com.mbs.qlcc.usecases.output.Organization;

import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.entities.User.User;

import java.util.List;

public interface IOrgUserDsGateway {
    OrgUser findUserByOrgId(String userId, String orgId);
    String saveOrgUser(List<OrgUser> orgUser);
    OrgUser updateOrgUser(OrgUser orgUser);
    void deleteOrgUser(List<String> userId, String orgId);
    List<String> getAllUserIdsByOrgId(String orgId, List<String> roleIds);
//    String getRoleIdByUserIdAndOrgId(String userId, String orgId);
}
