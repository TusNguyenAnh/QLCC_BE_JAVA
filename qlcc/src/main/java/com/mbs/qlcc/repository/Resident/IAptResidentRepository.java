package com.mbs.qlcc.repository.Resident;

import com.mbs.qlcc.domain.AptResident;
import com.mbs.qlcc.dto.response.IResUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAptResidentRepository extends JpaRepository<AptResident, String> {
    @Query("SELECT DISTINCT u.id as id, r.complexId as complexId, r.fullname as fullname, r.gender as gender, " +
            "r.email as email, r.birthday as birthday, r.relationship as relationship, r.phoneNumber as phoneNumber, r.cccd as cccd, r.id as userId " +
            "FROM AptResident ar " +
            "JOIN Resident r ON ar.resident.id = r.id " +
            "JOIN Apartment a ON ar.apartment.id = a.id " +
            "JOIN User u ON u.resId = r.id " +
            "WHERE a.building.id IN :buildingId " +
            "AND NOT EXISTS (" +
            "  SELECT 1 FROM OrgUser ou WHERE ou.userId = u.id AND ou.orgId = :orgId" +
            ")")
    List<IResUser> findResidentsInBuildingNotInOrg(
            @Param("buildingId") List<String> buildingId,
            @Param("orgId") String orgId);
}
