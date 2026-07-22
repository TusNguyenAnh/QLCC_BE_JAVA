package com.mbs.qlcc.adapters.db.Resident;

import com.mbs.qlcc.adapters.response.IResUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface JpaAptResidentRepository extends JpaRepository<AptResidentDataMapper, String> {
    @Query("SELECT DISTINCT u.id as id, r.complexId as complexId, r.fullname as fullname, r.gender as gender, " +
            "r.email as email, r.birthday as birthday, r.relationship as relationship, r.phoneNumber as phoneNumber, r.cccd as cccd, r.id as userId " +
            "FROM AptResidentDataMapper ar " +
            "JOIN ResidentDataMapper r ON ar.residentDataMapper.id = r.id " +
            "JOIN ApartmentDataMapper a ON ar.apartmentDataMapper.id = a.id " +
            "JOIN UserDataMapper u ON u.resId = r.id " +
            "WHERE a.buildingDataMapper.id IN :buildingId " +
            "AND NOT EXISTS (" +
            "  SELECT 1 FROM OrgUserDataMapper ou WHERE ou.userId = u.id AND ou.orgId = :orgId" +
            ")")
    List<IResUser> findResidentsInBuildingNotInOrg(
            @Param("buildingId") List<String> buildingId,
            @Param("orgId") String orgId);
}
