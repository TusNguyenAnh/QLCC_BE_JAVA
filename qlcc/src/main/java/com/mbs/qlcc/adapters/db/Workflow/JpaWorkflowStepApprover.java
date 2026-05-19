package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.adapters.db.Authentication.RoleDataMapper;
import com.mbs.qlcc.entities.Workflow.WorkflowStepApprover;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowStepApproverDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaWorkflowStepApprover implements IWorkflowStepApproverDsGateway {
    private final JpaWorkflowStepApproverRepository repository;

    @Override
    public List<String> getPositionByWfStep(String wfStepId) {
        return repository.getPositionByWorkflowStepDataMapper_Id(wfStepId);
    }

    @Override
    public void saveAll(List<WorkflowStepApprover> workflowStepApprovers) {
        List<WorkflowStepApproverDataMapper> dataMappers = workflowStepApprovers.stream()
                .map(this::mapToDataMapper)
                .toList();
        repository.saveAll(dataMappers);
    }

    private WorkflowStepApprover mapToEntity(WorkflowStepApproverDataMapper dataMapper) {
        return new WorkflowStepApprover(
                dataMapper.getId(),
                dataMapper.getWorkflowStepDataMapper().getId(),
                dataMapper.getRoleDataMapper().getId(),
                dataMapper.getStatus(),
                dataMapper.getCreatedAt(),
                dataMapper.getUpdatedAt(),
                dataMapper.getDeletedAt()
        );
    }

    private WorkflowStepApproverDataMapper mapToDataMapper(WorkflowStepApprover entity) {
        return WorkflowStepApproverDataMapper.builder()
                .workflowStepDataMapper(WorkflowStepDataMapper.builder().id(entity.getWorkflowStepId()).build())
                .roleDataMapper(RoleDataMapper.builder().id(entity.getPosition()).build())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }
}
