package com.mbs.qlcc.repository.Staff;

import com.mbs.qlcc.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, String> {

    @Query("SELECT s.id FROM Staff s WHERE s.complexId = :complexId AND s.email IN :emails AND s.deletedAt IS NULL")
    List<String> findEmailsByComplexId(@Param("complexId") String complexId, @Param("emails") List<String> emails);

    @Query("SELECT s.id FROM Staff s WHERE s.complexId = :complexId AND s.phoneNumber IN :phoneNumbers AND s.deletedAt IS NULL")
    List<String> findPhoneNumbersByComplexId(@Param("complexId") String complexId, @Param("phoneNumbers") List<String> phoneNumbers);
}
