package com.mbs.qlcc.repository.Resident;

import com.mbs.qlcc.domain.Resident;
import com.mbs.qlcc.dto.response.IResAptBd;
import com.mbs.qlcc.dto.response.IResUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface IResidentRepository extends JpaRepository<Resident, String>,
        JpaSpecificationExecutor<Resident> {
    @Query("SELECT DISTINCT u.id as id, r.complexId as complexId, r.fullname as fullname, r.gender as gender, " +
            "r.email as email, r.birthday as birthday, r.relationship as relationship, r.phoneNumber as phoneNumber, r.cccd as cccd, r.id as userId " +
            "FROM Resident r " +
            "JOIN User u ON r.id = u.resId " +
            "LEFT JOIN OrgUser ou ON ou.userId = u.id " +
            "WHERE ou.orgId = :orgId")
    List<IResUser> findResUserByOrgId(String orgId);

    @Query("SELECT r.id as id, r.complexId as complexId, r.fullname as fullname, r.gender as gender, r.email as email, " +
            "r.birthday as birthday, r.relationship as relationship, r.phoneNumber as phoneNumber, r.cccd as cccd ,a.floor as floor, a.aptNumber as aptNumber, " +
            "b.id AS buildingId, r.status as status " +
            "FROM Resident r " +
            "JOIN AptResident ar ON r.id = ar.resident.id " +
            "JOIN Apartment a ON ar.apartment.id = a.id " +
            "JOIN Building b ON a.building.id = b.id " +
            "WHERE r.complexId = :complexId " +
            "AND (:buildingId IS NULL OR b.id = :buildingId) " +
            "AND (:floor = 0 OR a.floor = :floor) " +
            "AND (:aptNumber IS NULL OR a.aptNumber = :aptNumber) " +
            "AND (:relationship IS NULL OR r.relationship = :relationship) " +
            "ORDER BY r.createdAt DESC")
    List<IResAptBd> filter(String complexId, String buildingId, int floor, String aptNumber, String relationship);


    // Check duplicate fields
    @Query("SELECT r.email FROM Resident r WHERE r.complexId = :complexId AND r.email IN :emails AND r.deletedAt IS NULL")
    List<String> findEmailsByComplexId(
            @Param("complexId") String complexId,
            @Param("emails") Collection<String> emails);

    @Query("SELECT r.phoneNumber FROM Resident r WHERE r.complexId = :complexId AND r.phoneNumber IN :phoneNumbers AND r.deletedAt IS NULL")
    List<String> findPhoneNumbersByComplexId(
            @Param("complexId") String complexId,
            @Param("phoneNumbers") Collection<String> phoneNumbers);

    @Query("SELECT r FROM Resident r WHERE r.complexId = :complexId AND r.cccd IN :cccds AND r.deletedAt IS NULL")
    List<Resident> findByComplexIdAndCccdIn(
            @Param("complexId") String complexId,
            @Param("cccds") Collection<String> cccds);


    // Existence checks
    boolean existsByComplexIdAndEmail(String complexId, String email);

    boolean existsByComplexIdAndPhoneNumber(String complexId, String phoneNumber);

    boolean existsByComplexIdAndCccd(String complexId, String cccd);
}
