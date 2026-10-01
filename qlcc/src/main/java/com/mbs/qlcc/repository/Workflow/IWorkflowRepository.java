package com.mbs.qlcc.repository.Workflow;

import com.mbs.qlcc.domain.Workflow;
import com.mbs.qlcc.dto.response.Workflow.WorkflowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkflowRepository extends JpaRepository<Workflow, String> {

    @EntityGraph(attributePaths = {
            "workflowSteps",
            "workflowSteps.workflowStepApprovers",
            "workflowSteps.workflowStepApprovers.role"
    })
    Page<WorkflowResponse> findByComplexIdAndDeletedAtIsNull(String complexId, Pageable pageable);
}
