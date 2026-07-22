package com.mbs.qlcc.adapters.db.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaStaffRepository extends JpaRepository<StaffDataMapper, String> {
    @Query("SELECT s.id FROM StaffDataMapper s WHERE s.complexId = :complexId AND s.email IN :emails")
    List<String> findIdsByComplexIdAndEmails(
            @Param("complexId") String complexId,
            @Param("emails") List<String> emails);

    @Query("SELECT s.id FROM StaffDataMapper s WHERE s.complexId = :complexId AND s.phoneNumber IN :phoneNumbers")
    List<String> findIdsByComplexIdAndPhoneNumbers(
            @Param("complexId") String complexId,
            @Param("phoneNumbers") List<String> phoneNumbers);
}
