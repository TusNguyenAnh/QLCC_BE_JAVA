package com.mbs.qlcc.adapters.db.Organization;

import com.mbs.qlcc.adapters.db.Token.JpaTokenRepository;
import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JpaOrgUser implements IOrgUserDsGateway {
    final JpaOrgUserRepository repository;

    @Override
    public OrgUser findUserByOrgId(String userId, String orgId) {
        OrgUserDataMapper orgUserDataMapper = repository.findByUserIdAndOrgId(userId, orgId);
        return toOrgUser(orgUserDataMapper);
    }


    private static OrgUser toOrgUser(OrgUserDataMapper orgUserMap) {
        return new OrgUser(orgUserMap.getUserId(), orgUserMap.getRoleId(), orgUserMap.getOrgId());
    }
}
