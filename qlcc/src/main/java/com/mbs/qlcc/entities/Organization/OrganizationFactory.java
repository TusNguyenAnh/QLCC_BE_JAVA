package com.mbs.qlcc.entities.Organization;

import java.util.List;

public class OrganizationFactory implements IOrganizationFactory {
    @Override
    public Organization create(String orgCode, String orgName, String complexId, String parentOrgId,
                               String description, int level) {
        return new Organization(orgCode, orgName, complexId, parentOrgId, description, "0", level);
    }
}
