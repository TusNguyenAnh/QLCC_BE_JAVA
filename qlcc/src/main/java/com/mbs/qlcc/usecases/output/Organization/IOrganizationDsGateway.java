package com.mbs.qlcc.usecases.output.Organization;

import com.mbs.qlcc.entities.Organization.Organization;
import com.mbs.qlcc.usecases.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
public interface IOrganizationDsGateway {

    PageResponse<Organization> show(String complexId, int page, int perPage);
    

    List<Organization> getAllWithoutDescendants(String parentOrgId, String complexId);
    

    Optional<Organization> getById(String id);
    

    Integer getTopLevel(String complexId);
    

    Organization store(Organization organization);
    

    Organization update(Organization organization);
    

    void delete(List<String> organizationIds);
    

    boolean existsRootOrg(String complexId);
    

    boolean existsOrgCode(String complexId, String orgCode);

    List<Organization> findByComplexIdAndStatus(String complexId, String status);
}
