package com.mbs.qlcc.entities.Organization;

import java.util.List;

/**
 * Organization Factory Interface
 * Defines contract for creating Organization entities
 */
public interface IOrganizationFactory {
    Organization create(String orgCode, String orgName, String complexId, String parentOrgId,
                        String description, int level);


}
