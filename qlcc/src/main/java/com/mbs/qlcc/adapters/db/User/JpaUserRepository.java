package com.mbs.qlcc.adapters.db.User;

import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JpaUserRepository extends JpaRepository<UserDataMapper, String> {
    UserDataMapper findByUsernameAndComplexId(String username, String complexId);

    @Query(value = "SELECT DISTINCT ob.building_id FROM users u " +
            "JOIN residents r ON u.res_id = r.id " +
            "JOIN org_building ob ON r.org_id = ob.org_id " +
            "WHERE u.id = :userId", nativeQuery = true)
    List<String> findBuildingIdsManage(@Param("userId") String userId);

    @Query(value = "SELECT u.id as id, u.username as username, u.complex_id as complexId, u.staff_id as staffId, s.email as email, s.phone_number as phoneNumber, s.fullname as fullname " +
            "FROM users u " +
            "JOIN staffs s ON u.staff_id = s.id " +
            "JOIN org_user ou ON u.id = ou.user_id " +
            "WHERE u.is_deleted = 0 AND ou.org_id = :orgId", nativeQuery = true)
    List<IStaffUserResponse> findStaffByOrgId(@Param("orgId") String orgId);

    @Query(value = "SELECT u.id as id, u.username as username, u.complex_id as complexId, u.staff_id as staffId, r.email as email, r.phone_number as phoneNumber, r.fullname as fullname " +
            "FROM users u " +
            "JOIN residents r ON u.res_id = r.id " +
            "JOIN org_user ou ON u.id = ou.user_id " +
            "WHERE u.is_deleted = 0 AND ou.org_id = :orgId", nativeQuery = true)
    List<IResUserResponse> findResByOrgId(@Param("orgId") String orgId);

    @Query(value = "SELECT u " +
            "FROM UserDataMapper u " +
            "WHERE u.username IN :usernames AND u.complexId = :complexId")
    List<UserDataMapper> getUserIdByUsername(@Param("usernames") Set<String> usernames, @Param("complexId") String complexId);

    @Query(value = "SELECT u.id as id, u.username as username, u.complex_id as complexId, u.staff_id as staffId, " +
            "r.email as email, r.phone_number as phoneNumber, r.fullname as fullname " +
            "FROM users u " +
            "JOIN residents r ON u.res_id = r.id " +
            "JOIN apt_res ar ON r.id = ar.resident_id " +
            "JOIN apartments a ON ar.apt_id = a.id " +
            "JOIN buildings b ON a.building_id = b.id " +
            "WHERE r.complex_id = :complexId " +
            "AND (:buildingId IS NULL OR b.id = :buildingId) " +
            "AND (:floor IS NULL OR a.floor = :floor) " +
            "AND (:aptNumber IS NULL OR a.apt_number = :aptNumber) " +
            "AND (:relationship IS NULL OR r.relationship = :relationship) ", nativeQuery = true)
    List<IResUserResponse> filterUser(@Param("complexId") String complexId, @Param("buildingId") Object buildingId, @Param("floor") Object floor,
                                      @Param("aptNumber") Object aptNumber, @Param("relationship") Object relationship);
}
