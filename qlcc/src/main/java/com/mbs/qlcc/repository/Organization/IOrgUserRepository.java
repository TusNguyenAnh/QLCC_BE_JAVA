package com.mbs.qlcc.repository.Organization;

import com.mbs.qlcc.domain.OrgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrgUserRepository extends JpaRepository<OrgUser, String> {
    OrgUser findByUserIdAndOrgId(String userId, String orgId);
    List<OrgUser> findByOrgIdAndRoleIdIn(String orgId, List<String> roleIds);
    List<OrgUser> findByOrgIdAndUserIdIn(String orgId, List<String> userIds);
}
