package com.mbs.qlcc.adapters.db.Organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrgUserRepository extends JpaRepository<OrgUserDataMapper, String> {
    OrgUserDataMapper findByUserIdAndOrgId(String userId,String orgId);
    List<OrgUserDataMapper> findByOrgIdAndRoleIdIn(String orgId, List<String> roleIds);
    List<OrgUserDataMapper> findByOrgIdAndUserIdIn(String orgId, List<String> userIds);

}
