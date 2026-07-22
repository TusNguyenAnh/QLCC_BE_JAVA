package com.mbs.qlcc.adapters.db.Task;

import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.Task.Task;
import com.mbs.qlcc.usecases.output.Task.ITaskDsGateway;
import com.mbs.qlcc.usecases.request.Task.TaskFilterInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Task.ITaskOrgResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JpaTask implements ITaskDsGateway {
    private final JpaTaskRepository repository;
    private final EntityManager entityManager;

    @Override
    public Task createTask(Task task) {
        TaskDataMapper mapper = repository.save(mapToMapper(task));
        return mapToEntity(mapper);
    }

    @Override
    public Task getTaskById(String taskId) {
        return mapToEntity(repository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId)));
    }

    @Override
    public Task updateTask(Task task) {
        TaskDataMapper mapper = TaskDataMapper.builder()
                .id(task.getId())
                .complexId(task.getComplexId())
                .taskTypeId(task.getTaskTypeId())
                .currentOrgId(task.getCurrentOrgId())
                .creator(task.getCreator())
                .currentStep(task.getCurrentStep())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .status(task.getStatus())
                .category(task.getCategory())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .deletedAt(task.getDeletedAt())
                .isDeleted(task.isDeleted())
                .build();
        return mapToEntity(repository.save(mapper));
    }

    @Override
    public PageResponse<ITaskOrgResponse> getTasksByOrgId(TaskFilterInpRequest request, String approverId, String orgId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "created_at"
        );

        Map<String, Object> params = new HashMap<>();
        if (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) {
            params.put("priorityIds", request.getPriorityId());
        }

        if (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) {
            params.put("taskTypeIds", request.getTaskTypeId());
        }

        if (request.getTimeApprovedStart() != null) {
            params.put("approvedStart", request.getTimeApprovedStart());
        }

        if (request.getTimeApprovedEnd() != null) {
            params.put("approvedEnd", request.getTimeApprovedEnd());
        }

        if (request.getTimeRequestStart() != null) {
            params.put("requestStart", request.getTimeRequestStart());
        }

        if (request.getTimeRequestEnd() != null) {
            params.put("requestEnd", request.getTimeRequestEnd());
        }

        Page<ITaskOrgResponse> result = repository.getByOrgId(
                orgId, status, approverId, params.get("priorityIds"),
                params.get("taskTypeIds"), params.get("approvedStart"),
                params.get("approvedEnd"), params.get("requestStart"),
                params.get("requestEnd"), pageable
        );

        return new PageResponse<ITaskOrgResponse>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public PageResponse<ITaskOrgResponse> getTasksByCreator(TaskFilterInpRequest request, String creator, String status, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "t.created_at"
        );

        Map<String, Object> params = new HashMap<>();
        if (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) {
            params.put("priorityIds", request.getPriorityId());
        }

        if (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) {
            params.put("taskTypeIds", request.getTaskTypeId());
        }

        if (request.getTimeApprovedStart() != null) {
            params.put("approvedStart", request.getTimeApprovedStart());
        }

        if (request.getTimeApprovedEnd() != null) {
            params.put("approvedEnd", request.getTimeApprovedEnd());
        }

        if (request.getTimeRequestStart() != null) {
            params.put("requestStart", request.getTimeRequestStart());
        }

        if (request.getTimeRequestEnd() != null) {
            params.put("requestEnd", request.getTimeRequestEnd());
        }

        Page<ITaskOrgResponse> result = repository.getByCreator(
                creator, status, params.get("priorityIds"),
                params.get("taskTypeIds"), params.get("approvedStart"),
                params.get("approvedEnd"), params.get("requestStart"),
                params.get("requestEnd"), pageable
        );

        return new PageResponse<ITaskOrgResponse>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    private Task mapToEntity(TaskDataMapper mapper) {
        return new Task(
                mapper.getId(),
                mapper.getComplexId(),
                mapper.getTaskTypeId(),
                mapper.getCurrentOrgId(),
                mapper.getCreator(),
                mapper.getCurrentStep(),
                mapper.getTaskName(),
                mapper.getDescription(),
                mapper.getStatus(),
                mapper.getCategory(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt(),
                mapper.isDeleted()
        );
    }

    private TaskDataMapper mapToMapper(Task task) {
        return TaskDataMapper.builder()
                .complexId(task.getComplexId())
                .taskTypeId(task.getTaskTypeId())
                .currentOrgId(task.getCurrentOrgId())
                .creator(task.getCreator())
                .currentStep(task.getCurrentStep())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .status(task.getStatus())
                .category(task.getCategory())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .deletedAt(task.getDeletedAt())
                .isDeleted(task.isDeleted())
                .build();
    }

//    StringBuilder sql = new StringBuilder(
//            "SELECT t.id as id, t.complex_id as complexId, t.tasktype_id as taskTypeId, t.current_org_id as currentOrgId, " +
//                    "t.creator as creator, t.current_step as currentStep, t.task_name as taskName, t.description as description, " +
//                    "t.status as status, t.category as category, tt.type_name as typeName, p.priority_name as priorityName, " +
//                    "u.username as username, r.phone_number as phoneNumber, r.fullname as fullName, a.apt_number as aptNumber, " +
//                    "o.level as level, b.building_name as buildingName " +
//                    "FROM task t " +
//                    "JOIN task_type tt ON t.tasktype_id = tt.id " +
//                    "JOIN priority p ON tt.priority_id = p.id " +
//                    "JOIN organization o ON t.current_org_id = o.id " +
//                    "JOIN users u ON t.creator = u.id " +
//                    "JOIN residents r ON u.res_id = r.id " +
//                    "JOIN apt_res ar ON ar.resident_id = r.id " +
//                    "JOIN apartments a ON ar.apt_id = a.id " +
//                    "JOIN buildings b ON a.building_id = b.id " +
//                    "JOIN task_history th ON th.task_id = t.id " +
//                    "WHERE t.current_step = th.step_order ");
//
//    Map<String, Object> params = new HashMap<>();
//
//        if (orgId != null) {
//        sql.append("AND t.current_org_id = :orgId ");
//        params.put("orgId", orgId);
//    }
//
//        if (status != null) {
//        sql.append("AND th.action = :status ");
//        params.put("status", status);
//    }
//
//        if (approverId != null) {
//        sql.append("AND th.approver_id = :approverId ");
//        params.put("approverId", approverId);
//    }
//
//        if (request.getPriorityId() != null && !request.getPriorityId().isEmpty()) {
//        sql.append("AND p.id IN (:priorityIds) ");
//        params.put("priorityIds", request.getPriorityId());
//    }
//
//        if (request.getTaskTypeId() != null && !request.getTaskTypeId().isEmpty()) {
//        sql.append("AND t.tasktype_id IN (:taskTypeIds) ");
//        params.put("taskTypeIds", request.getTaskTypeId());
//    }
//
//        if (request.getTimeApprovedStart() != null) {
//        sql.append("AND t.updated_at >= :approvedStart ");
//        params.put("approvedStart", request.getTimeApprovedStart());
//    }
//
//        if (request.getTimeApprovedEnd() != null) {
//        sql.append("AND t.updated_at <= :approvedEnd ");
//        params.put("approvedEnd", request.getTimeApprovedEnd());
//    }
//
//        if (request.getTimeRequestStart() != null) {
//        sql.append("AND t.created_at >= :requestStart ");
//        params.put("requestStart", request.getTimeRequestStart());
//    }
//
//        if (request.getTimeRequestEnd() != null) {
//        sql.append("AND t.created_at <= :requestEnd ");
//        params.put("requestEnd", request.getTimeRequestEnd());
//    }
//
//        if (request.getOrder() != null) {
//        sql.append("ORDER BY t.created_at ").append(request.getOrder());
//    } else {
//        sql.append("ORDER BY t.created_at DESC ");
//    }
//
//    Query query = entityManager.createNativeQuery(sql.toString());
//
//        params.forEach(query::setParameter);
//
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//
//    List<Object[]> result = query.getResultList();
//
//    List<TaskOrgResponse> content = result.stream().map(row -> new TaskOrgResponse(
//            (String) row[0], // id
//            (String) row[1], // complexId
//            (String) row[2], // taskTypeId
//            (String) row[3], // currentOrgId
//            (String) row[4], // creator
//            (Integer) row[5], // currentStep
//            (String) row[6], // taskName
//            (String) row[7], // description
//            (String) row[8], // status
//            (String) row[9], // category
//            (String) row[10], // typeName
//            (String) row[11], // priorityName
//            (String) row[12], // username
//            (String) row[13], // phoneNumber
//            (String) row[14], // fullName
//            (String) row[15], // aptNumber
//            (Integer) row[16], // level
//            (String) row[17]  // buildingName
//    )).toList();
}
