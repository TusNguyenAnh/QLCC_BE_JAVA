package com.mbs.qlcc.adapters.db.Task;

import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaTaskRepository extends JpaRepository<TaskDataMapper, String> {
    @Query(value = "SELECT t.id as id, t.complex_id as complexId, t.tasktype_id as taskTypeId, t.current_org_id as currentOrgId, " +
            "t.creator as creator, t.current_step as currentStep, t.task_name as taskName, t.description as description, " +
            "t.status as status, t.category as category, tt.type_name as typeName, p.priority_name as priorityName, " +
            "u.username as username, r.phone_number as phoneNumber, r.fullname as fullName, a.apt_number as aptNumber, " +
            "b.building_name as buildingName, o.level as level " +
            "FROM task t " +
            "JOIN task_type tt ON t.tasktype_id = tt.id " +
            "JOIN priority p ON tt.priority_id = p.id " +
            "JOIN organization o ON t.current_org_id = o.id " +
            "JOIN users u ON t.creator = u.id " +
            "JOIN residents r ON u.res_id = r.id " +
            "JOIN apt_res ar ON ar.resident_id = r.id " +
            "JOIN apartments a ON ar.apt_id = a.id " +
            "JOIN buildings b ON a.building_id = b.id " +
            "JOIN task_history th ON th.task_id = t.id " +
            "WHERE t.current_org_id = :orgId " +
            "AND t.current_step = th.step_order " +
            "AND th.action = :status " +
            "AND th.approver_id = :approverId " +
            "AND (:priorityIds IS NULL OR p.id IN (:priorityIds)) " +
            "AND (:taskTypeIds IS NULL OR t.tasktype_id IN (:taskTypeIds)) " +
            "AND (:approvedStart IS NULL OR t.updated_at >= :approvedStart) " +
            "AND (:approvedEnd IS NULL OR t.updated_at <= :approvedEnd) " +
            "AND (:requestStart IS NULL OR t.created_at >= :requestStart) " +
            "AND (:requestEnd IS NULL OR t.created_at <= :requestEnd) ",

            countQuery = "SELECT COUNT(DISTINCT t.id) " +
                    "FROM task t " +
                    "JOIN task_type tt ON t.tasktype_id = tt.id " +
                    "JOIN priority p ON tt.priority_id = p.id " +
                    "JOIN task_history th ON th.task_id = t.id " +
                    "WHERE t.current_org_id = :orgId " +
                    "AND t.current_step = th.step_order " +
                    "AND th.action = :status " +
                    "AND th.approver_id = :approverId " +
                    "AND (:priorityIds IS NULL OR p.id IN (:priorityIds)) " +
                    "AND (:taskTypeIds IS NULL OR t.tasktype_id IN (:taskTypeIds)) " +
                    "AND (:approvedStart IS NULL OR t.updated_at >= :approvedStart) " +
                    "AND (:approvedEnd IS NULL OR t.updated_at <= :approvedEnd) " +
                    "AND (:requestStart IS NULL OR t.created_at >= :requestStart) " +
                    "AND (:requestEnd IS NULL OR t.created_at <= :requestEnd) ", nativeQuery = true)
    Page<ITaskOrgResponse> getByOrgId(@Param("orgId") String orgId, @Param("status") String status, @Param("approverId") String approverId,
                                      @Param("priorityIds") Object priorityIds, @Param("taskTypeIds") Object taskTypeIds,
                                      @Param("approvedStart") Object approvedStart, @Param("approvedEnd") Object approvedEnd,
                                      @Param("requestStart") Object requestStart, @Param("requestEnd") Object requestEnd, Pageable pageable);


    @Query(value = "SELECT t.id as id, t.complex_id as complexId, t.tasktype_id as taskTypeId, t.current_org_id as currentOrgId, " +
            "t.creator as creator, t.current_step as currentStep, t.task_name as taskName, t.description as description, " +
            "t.status as status, t.category as category, tt.type_name as typeName, p.priority_name as priorityName, " +
            "u.username as username, r.phone_number as phoneNumber, r.fullname as fullName, a.apt_number as aptNumber, " +
            "b.building_name as buildingName " +
            "FROM task t " +
            "JOIN task_type tt ON t.tasktype_id = tt.id " +
            "JOIN priority p ON tt.priority_id = p.id " +
            "JOIN users u ON t.creator = u.id " +
            "JOIN residents r ON u.res_id = r.id " +
            "JOIN apt_res ar ON ar.resident_id = r.id " +
            "JOIN apartments a ON ar.apt_id = a.id " +
            "JOIN buildings b ON a.building_id = b.id " +
            "WHERE t.creator = :creator " +
            "AND t.status = :status " +
            "AND t.category = 'task' " +
            "AND (:priorityIds IS NULL OR p.id IN (:priorityIds)) " +
            "AND (:taskTypeIds IS NULL OR t.tasktype_id IN (:taskTypeIds)) " +
            "AND (:approvedStart IS NULL OR t.updated_at >= :approvedStart) " +
            "AND (:approvedEnd IS NULL OR t.updated_at <= :approvedEnd) " +
            "AND (:requestStart IS NULL OR t.created_at >= :requestStart) " +
            "AND (:requestEnd IS NULL OR t.created_at <= :requestEnd) ",

            countQuery = "SELECT COUNT(DISTINCT t.id) " +
                    "FROM task t " +
                    "JOIN task_type tt ON t.tasktype_id = tt.id " +
                    "JOIN priority p ON tt.priority_id = p.id " +
                    "WHERE t.creator = :creator " +
                    "AND t.status = :status " +
                    "AND t.category = 'task' " +
                    "AND (:priorityIds IS NULL OR p.id IN (:priorityIds)) " +
                    "AND (:taskTypeIds IS NULL OR t.tasktype_id IN (:taskTypeIds)) " +
                    "AND (:approvedStart IS NULL OR t.updated_at >= :approvedStart) " +
                    "AND (:approvedEnd IS NULL OR t.updated_at <= :approvedEnd) " +
                    "AND (:requestStart IS NULL OR t.created_at >= :requestStart) " +
                    "AND (:requestEnd IS NULL OR t.created_at <= :requestEnd) ", nativeQuery = true)
    Page<ITaskOrgResponse> getByCreator(@Param("creator") String creator, @Param("status") String status,
                                        @Param("priorityIds") Object priorityIds, @Param("taskTypeIds") Object taskTypeIds,
                                        @Param("approvedStart") Object approvedStart, @Param("approvedEnd") Object approvedEnd,
                                        @Param("requestStart") Object requestStart, @Param("requestEnd") Object requestEnd, Pageable pageable);
}
