package com.mbs.qlcc.repository.Workflow;

import com.mbs.qlcc.domain.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWorkflowStepRepository extends JpaRepository<WorkflowStep, String> {
    List<WorkflowStep> findByWorkflow_IdOrderByStepOrderAsc(String workflowId);
}
