package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.entities.Workflow.Workflow;
import com.mbs.qlcc.usecases.output.Workflow.IWorkflowDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Workflow.WorkflowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaWorkflow implements IWorkflowDsGateway {
    private final JpaWorkflowRepository workflowRepository;

    @Override
    public PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "createdAt"
        );

        Page<WorkflowResponse> workflowPage = workflowRepository.findByComplexId(complexId, pageable);
        return new PageResponse<WorkflowResponse>(
                workflowPage.getContent(),
                workflowPage.getNumber(),
                workflowPage.getSize(),
                workflowPage.getTotalElements(),
                workflowPage.getTotalPages()
        );
    }

    @Override
    public Workflow save(Workflow workflow) {
        WorkflowDataMapper saved = workflowRepository.save(mapToData(workflow));
        return mapToDomain(saved);
    }

    private Workflow mapToDomain(WorkflowDataMapper mapper) {
        return new Workflow(
                mapper.getId(),
                mapper.getComplexId(),
                mapper.getWorkflowName(),
                mapper.getDescription(),
                mapper.getStatus(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }

    private WorkflowDataMapper mapToData(Workflow workflow) {
        return WorkflowDataMapper.builder()
                .workflowName(workflow.getWorkflowName())
                .complexId(workflow.getComplexId())
                .description(workflow.getDescription())
                .status(workflow.getStatus())
                .createdAt(workflow.getCreatedAt())
                .updatedAt(workflow.getUpdatedAt())
                .deletedAt(workflow.getDeletedAt())
                .build();
    }
}
