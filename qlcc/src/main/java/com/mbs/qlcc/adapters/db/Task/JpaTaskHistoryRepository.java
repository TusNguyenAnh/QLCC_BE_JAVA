package com.mbs.qlcc.adapters.db.Task;

import com.mbs.qlcc.usecases.response.Task.ITaskHistoryResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaTaskHistoryRepository extends JpaRepository<TaskHistoryDataMapper, String> {
    TaskHistoryDataMapper findFirstByTaskIdAndStepOrder(String taskId, int stepOrder);

    TaskHistoryDataMapper findByTaskIdAndOrgIdAndApproverId(String taskId, String orgId, String approverId);

    @Query("SELECT t FROM TaskHistoryDataMapper t WHERE t.taskId = :taskId AND t.stepOrder > :stepOrder")
    List<TaskHistoryDataMapper> findTaskForReject(@Param("taskId") String taskId, @Param("stepOrder") int stepOrder);

    @Query("SELECT EXISTS ( " +
            "SELECT 1 " +
            "FROM TaskHistoryDataMapper t " +
            "WHERE t.taskId = :taskId " +
            "AND t.stepOrder = :stepOrder " +
            "AND t.action <> :action)")
    boolean checkAllApprovedInStep(@Param("taskId") String taskId, @Param("stepOrder") int stepOrder, @Param("action") String action);

    @Query(value = "SELECT th.id as id, th.task_id as taskId, th.approver_id as approverId, th.org_id as orgId, " +
            "th.step_order as stepOrder, th.action as action, th.comment as comment, " +
            "r.role_name as roleName, " +
            "o.org_name as orgName, o.level as level, " +
            "wf.workflow_name as workflowName, " +
            "COALESCE(s.fullname, r.fullname) AS fullname " +
            "FROM task_history th " +
            "JOIN task t ON th.task_id = t.id " +
            "JOIN organization o ON th.org_id = o.id " +
            "JOIN task_type tt ON t.tasktype_id = tt.id " +
            "JOIN workflow wf ON tt.workflow_id = wf.id " +
            "JOIN org_user ou ON th.approver_id = ou.user_id AND th.org_id = ou.org_id " +
            "JOIN roles r ON r.id = ou.role_id " +
            "JOIN users u ON th.approver_id = u.id " +
            "LEFT JOIN staffs s ON u.staff_id = s.id " +
            "LEFT JOIN residents res ON u.res_id = res.id " +
            "WHERE th.task_id = :taskId " +
            "ORDER BY th.step_order ASC ", nativeQuery = true)
    List<ITaskHistoryResponse> getByTaskId(@Param("taskId") String taskId);

    @Query(value = "SELECT action, COUNT(*) AS total " +
            "FROM task_history " +
            "WHERE org_id = :orgId AND approver_id = :approverId " +
            "GROUP BY action " +
            "UNION ALL " +
            "SELECT 'ALL' AS action, COUNT(*) AS total " +
            "FROM task_history " +
            "WHERE org_id = :orgId AND approver_id = :approverId ", nativeQuery = true)
    List<ITaskSummaryResponse> taskActionSummary(@Param("orgId") String orgId, @Param("approverId") String approverId);

    @Query(value = "SELECT t.id as id, t.complex_id as complexId, t.tasktype_id as taskTypeId, t.current_org_id as currentOrgId, " +
            "t.creator as creator, t.current_step as currentStep, t.task_name as taskName, t.description as description, " +
            "t.status as status, t.category as category, tt.type_name as typeName, p.priority_name as priorityName, p.id as priorityId, " +
            "u.username as username, r.phone_number as phoneNumber, r.fullname as fullName, a.apt_number as aptNumber, " +
            "b.building_name as buildingName, o.level as level " +
            "FROM task_history th " +
            "JOIN task t ON th.task_id = t.id " +
            "JOIN task_type tt ON t.tasktype_id = tt.id " +
            "JOIN priority p ON tt.priority_id = p.id " +
            "JOIN organization o ON t.current_org_id = o.id " +
            "JOIN users u ON t.creator = u.id " +
            "JOIN residents r ON u.res_id = r.id " +
            "JOIN apt_res ar ON ar.resident_id = r.id " +
            "JOIN apartments a ON ar.apt_id = a.id " +
            "JOIN buildings b ON a.building_id = b.id " +
            "WHERE th.org_id = :orgId " +
            "AND th.action = :status " +
            "AND th.approver_id = :approverId " +
            "AND (:priorityIds IS NULL OR p.id IN (:priorityIds)) " +
            "AND (:taskTypeIds IS NULL OR t.tasktype_id IN (:taskTypeIds)) " +
            "AND (:approvedStart IS NULL OR t.updated_at >= :approvedStart) " +
            "AND (:approvedEnd IS NULL OR t.updated_at <= :approvedEnd) " +
            "AND (:requestStart IS NULL OR t.created_at >= :requestStart) " +
            "AND (:requestEnd IS NULL OR t.created_at <= :requestEnd) ",

            countQuery = "SELECT COUNT(DISTINCT t.id) " +
                    "FROM task_history th " +
                    "JOIN task t ON th.task_id = t.id " +
                    "JOIN task_type tt ON t.tasktype_id = tt.id " +
                    "JOIN priority p ON tt.priority_id = p.id " +
                    "WHERE th.org_id = :orgId " +
                    "AND th.action = :status " +
                    "AND th.approver_id = :approverId " +
                    "AND (:priorityIds IS NULL OR p.id IN (:priorityIds)) " +
                    "AND (:taskTypeIds IS NULL OR t.tasktype_id IN (:taskTypeIds)) " +
                    "AND (:approvedStart IS NULL OR t.updated_at >= :approvedStart) " +
                    "AND (:approvedEnd IS NULL OR t.updated_at <= :approvedEnd) " +
                    "AND (:requestStart IS NULL OR t.created_at >= :requestStart) " +
                    "AND (:requestEnd IS NULL OR t.created_at <= :requestEnd) ", nativeQuery = true)
    Page<ITaskOrgResponse> filterTaskApproved(@Param("orgId") String orgId, @Param("status") String status, @Param("approverId") String approverId,
                                              @Param("priorityIds") Object priorityIds, @Param("taskTypeIds") Object taskTypeIds,
                                              @Param("approvedStart") Object approvedStart, @Param("approvedEnd") Object approvedEnd,
                                              @Param("requestStart") Object requestStart, @Param("requestEnd") Object requestEnd, Pageable pageable);
}
