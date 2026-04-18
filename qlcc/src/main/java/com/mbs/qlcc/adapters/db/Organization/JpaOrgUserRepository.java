package com.mbs.qlcc.adapters.db.Organization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrgUserRepository extends JpaRepository<OrgUserDataMapper, String> {
    OrgUserDataMapper findByUserIdAndOrgId(String userId,String orgId);
}
