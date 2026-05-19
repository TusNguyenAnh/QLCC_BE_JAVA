package com.mbs.qlcc.adapters.db.Workflow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaWorkflowStepApproverRepository extends JpaRepository<WorkflowStepApproverDataMapper, String> {
    @Query("SELECT wsa.roleDataMapper.id FROM WorkflowStepApproverDataMapper wsa WHERE wsa.workflowStepDataMapper.id = :workflowStepId")
    List<String> getPositionByWorkflowStepDataMapper_Id(String workflowStepId);
}
