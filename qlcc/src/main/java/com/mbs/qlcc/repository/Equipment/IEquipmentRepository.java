package com.mbs.qlcc.repository.Equipment;

import com.mbs.qlcc.domain.Equipment;
import com.mbs.qlcc.dto.response.Equipment.IEquipmentHistoryResponse;
import com.mbs.qlcc.dto.response.Task.ITaskSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEquipmentRepository extends JpaRepository<Equipment, String> {
    Optional<Equipment> findByIdAndComplexIdAndDeletedAtIsNull(String id, String complexId);

    Page<Equipment> findByComplexIdAndDeletedAtIsNull(String complexId, Pageable pageable);

    List<Equipment> findAllByIdInAndComplexIdAndDeletedAtIsNull(Collection<String> ids, String complexId);

    /**
     * Tìm kiếm theo từ khóa (mã, tên, hạng mục) và hạng mục; tham số null thì bỏ qua điều kiện
     */
    @Query("SELECT e FROM Equipment e WHERE e.complexId = :complexId AND e.deletedAt IS NULL " +
            "AND (:category IS NULL OR e.category = :category) " +
            "AND (:keyword IS NULL OR LOWER(e.equipmentCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.equipmentName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Equipment> search(@Param("complexId") String complexId,
                           @Param("keyword") String keyword,
                           @Param("category") String category,
                           Pageable pageable);

    @Query(value = "SELECT e.pay_date AS expenseDate, e.description as description, e.total_amount as amount " +
            "FROM equipments eq " +
            "JOIN expenses e ON e.equipment_id = eq.id " +
            "WHERE e.complex_id = :complexId " +
            "AND eq.id = :equipmentId " +
            "AND (:category IS NULL OR e.category = :category) " +
            "ORDER BY e.pay_date DESC ", nativeQuery = true)
    List<IEquipmentHistoryResponse> getEquipmentHistory(@Param("equipmentId") String equipmentId, @Param("category") String category, @Param("complexId") String complexId);

    boolean existsByEquipmentCodeAndComplexIdAndDeletedAtIsNull(String equipmentCode, String complexId);

    boolean existsByEquipmentCodeAndComplexIdAndIdNotAndDeletedAtIsNull(String equipmentCode, String complexId, String id);

}