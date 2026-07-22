package com.mbs.qlcc.adapters.db.Organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrganizationRepository extends JpaRepository<OrganizationDataMapper, String> {
    //    @EntityGraph(attributePaths = {"buildings"})
    Page<OrganizationDataMapper> findByComplexIdAndParentOrgIdIsNullAndStatusEquals(
            String complexId, String status, Pageable pageable);

    Optional<OrganizationDataMapper> findByIdAndStatusEquals(String id, String status);

    boolean existsByComplexIdAndOrgCodeAndStatusEquals(String complexId, String orgCode, String status);

    boolean existsByComplexIdAndParentOrgIdIsNullAndStatusEquals(String complexId, String status);

    @Query(value = " WITH RECURSIVE excluded AS ( SELECT id FROM organization WHERE parent_org_id = :orgId " +
            "UNION ALL " +
            "SELECT o.id FROM organization o JOIN excluded e ON o.parent_org_id = e.id) " +
            "SELECT * " +
            "FROM organization " +
            "WHERE id NOT IN (SELECT id FROM excluded) AND id <> :orgId AND status = '0' AND complex_id = :complexId " +
            "ORDER BY level ASC;", nativeQuery = true)
    List<OrganizationDataMapper> findAllWithoutDescendants(@Param("orgId") String orgId,
                                                           @Param("complexId") String complexId);

    @Query(value = "SELECT COALESCE(MAX(level), 0) FROM organization WHERE complex_id = :complexId AND status = '0'", nativeQuery = true)
    Integer getMaxLevel(@Param("complexId") String complexId);

    List<OrganizationDataMapper> findByParentOrgIdAndStatusEquals(String parentOrgId, String status);

    List<OrganizationDataMapper> findByComplexIdAndStatusEquals(String complexId, String status);
}
