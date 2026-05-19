package com.mbs.qlcc.adapters.db.Workflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaWorkflowStepRepository extends JpaRepository<WorkflowStepDataMapper, String> {
    List<WorkflowStepDataMapper> findByWorkflowDataMapper_IdOrderByStepOrderAsc(String workflowId);
}
