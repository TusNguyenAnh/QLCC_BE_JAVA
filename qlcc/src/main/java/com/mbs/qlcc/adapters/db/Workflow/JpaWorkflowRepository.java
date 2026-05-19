package com.mbs.qlcc.adapters.db.Workflow;

import com.mbs.qlcc.usecases.response.Workflow.WorkflowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWorkflowRepository extends JpaRepository<WorkflowDataMapper, String> {
    @EntityGraph(attributePaths = {
            "workflowSteps",
            "workflowSteps.workflowStepApprovers",
            "workflowSteps.workflowStepApprovers.roleDataMapper"
    })
    Page<WorkflowResponse> findByComplexId(String complexId, Pageable pageable);
}
