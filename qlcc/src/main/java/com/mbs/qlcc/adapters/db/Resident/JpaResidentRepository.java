package com.mbs.qlcc.adapters.db.Resident;

import com.mbs.qlcc.adapters.response.IResAptBd;
import com.mbs.qlcc.adapters.response.IResUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaResidentRepository extends JpaRepository<ResidentDataMapper, String>,
        JpaSpecificationExecutor<ResidentDataMapper> {
    @Query("SELECT DISTINCT r, u.id AS userId FROM ResidentDataMapper r " +
            "JOIN UserDataMapper u ON r.id = u.resId " +
            "LEFT JOIN OrgUserDataMapper ou ON ou.userId = u.id " +
            "WHERE ou.orgId = :orgId")
    List<IResUser> findResUserByOrgId(String orgId);

    @Query("SELECT r, a.floor, a.aptNumber,b.id AS building_id " +
            "FROM ResidentDataMapper r " +
            "JOIN AptResidentDataMapper ar ON r.id = ar.residentDataMapper.id " +
            "JOIN ApartmentDataMapper a ON ar.apartmentDataMapper.id = a.id " +
            "JOIN BuildingDataMapper b ON a.buildingDataMapper.id = b.id " +
            "WHERE r.complexId = :complexId " +
            "AND (:buildingId IS NULL OR b.id = :buildingId) " +
            "AND (:floor IS NULL OR a.floor = :floor) " +
            "AND (:aptNumber IS NULL OR a.aptNumber = :aptNumber) " +
            "AND (:relationship IS NULL OR r.relationship = :relationship) ")
    Page<IResAptBd> filter(String complexId, String buildingId, int floor, String aptNumber, String relationship, Pageable pageable);


    // Check duplicate fields
    @Query("SELECT r.id FROM ResidentDataMapper r WHERE r.complexId = :complexId AND r.email IN :emails")
    List<String> findIdsByComplexIdAndEmails(
            @Param("complexId") String complexId,
            @Param("emails") List<String> emails);

    @Query("SELECT r.id FROM ResidentDataMapper r WHERE r.complexId = :complexId AND r.phoneNumber IN :phoneNumbers")
    List<String> findIdsByComplexIdAndPhoneNumbers(
            @Param("complexId") String complexId,
            @Param("phoneNumbers") List<String> phoneNumbers);

    @Query("SELECT r FROM ResidentDataMapper r WHERE r.complexId = :complexId AND r.cccd IN :cccds")
    List<ResidentDataMapper> findIdsByComplexIdAndCccds(
            @Param("complexId") String complexId,
            @Param("cccds") List<String> cccds);


    //    // Existence checks
    boolean existsByComplexIdAndEmail(String complexId, String email);

    boolean existsByComplexIdAndPhoneNumber(String complexId, String phoneNumber);

    boolean existsByComplexIdAndCccd(String complexId, String cccd);
}
