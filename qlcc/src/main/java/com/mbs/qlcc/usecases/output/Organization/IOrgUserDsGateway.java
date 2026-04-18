package com.mbs.qlcc.usecases.output.Organization;

import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.entities.User.User;

public interface IOrgUserDsGateway {
    OrgUser findUserByOrgId(String userId, String orgId);
}
