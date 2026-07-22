package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.entities.Workflow.WorkflowStep;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowStepDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaWorkflowStep implements IWorkflowStepDsGateway {
    private final JpaWorkflowStepRepository repository;

    @Override
    public List<WorkflowStep> getWorkflowStepsByWorkflowId(String workflowId) {
        List<WorkflowStepDataMapper> dataMappers = repository.findByWorkflowDataMapper_IdOrderByStepOrderAsc(workflowId);
        return dataMappers.stream()
                .map(this::mapToEntity)
                .toList();
    }

    @Override
    public void saveAll(List<WorkflowStep> workflowSteps) {
        List<WorkflowStepDataMapper> dataMappers = workflowSteps.stream()
                .map(this::mapToDataMapper)
                .toList();
        repository.saveAll(dataMappers);
    }

    private WorkflowStep mapToEntity(WorkflowStepDataMapper workflowStepDataMapper) {
        return new WorkflowStep(
                workflowStepDataMapper.getId(),
                workflowStepDataMapper.getWorkflowDataMapper().getId(),
                workflowStepDataMapper.getStepOrder(),
                workflowStepDataMapper.getOrgLevel(),
                workflowStepDataMapper.getDescription(),
                workflowStepDataMapper.getStatus(),
                workflowStepDataMapper.getCreatedAt(),
                workflowStepDataMapper.getUpdatedAt(),
                workflowStepDataMapper.getDeletedAt()
        );
    }

    private WorkflowStepDataMapper mapToDataMapper(WorkflowStep workflowStep) {
        return WorkflowStepDataMapper.builder()
                .id(workflowStep.getId())
                .workflowDataMapper(WorkflowDataMapper.builder().id(workflowStep.getWorkflowId()).build())
                .stepOrder(workflowStep.getStepOrder())
                .orgLevel(workflowStep.getOrgLevel())
                .description(workflowStep.getDescription())
                .status(workflowStep.getStatus())
                .createdAt(workflowStep.getCreatedAt())
                .updatedAt(workflowStep.getUpdatedAt())
                .deletedAt(workflowStep.getDeletedAt())
                .build();
    }
}
