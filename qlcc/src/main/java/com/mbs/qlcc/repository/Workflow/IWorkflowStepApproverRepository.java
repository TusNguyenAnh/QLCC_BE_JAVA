package com.mbs.qlcc.repository.Workflow;

import com.mbs.qlcc.domain.WorkflowStepApprover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWorkflowStepApproverRepository extends JpaRepository<WorkflowStepApprover, String> {

    @Query("SELECT wsa.role.id FROM WorkflowStepApprover wsa WHERE wsa.workflowStep.id = :workflowStepId")
    List<String> findRoleIdsByWorkflowStepId(String workflowStepId);
}
