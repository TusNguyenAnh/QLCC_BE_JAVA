package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Workflow.WorkflowRequest;
import com.mbs.qlcc.adapters.request.Workflow.WorkflowStepRequest;
import com.mbs.qlcc.usecases.input.IWorkflowInputBoundary;
import com.mbs.qlcc.usecases.request.Workflow.WorkflowInpRequest;
import com.mbs.qlcc.usecases.request.Workflow.WorkflowStepInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Workflow.WorkflowResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkflowService {
    IWorkflowInputBoundary useCase;

    public PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size) {
        return useCase.getByComplexId(complexId, page, size);
    }

    public String createWorkflow(String complexId, WorkflowRequest request) {
        List<WorkflowStepInpRequest> workflowStepInpRequest = request.getWorkflowSteps()
                .stream()
                .map(step -> new WorkflowStepInpRequest(
                        step.getOrgLevel(),
                        step.getStepOrder(),
                        step.getDescription(),
                        step.getPositions()
                )).toList();

        WorkflowInpRequest workflowInpRequest = new WorkflowInpRequest(
                complexId,
                request.getWorkflowName(),
                request.getDescription(),
                workflowStepInpRequest
        );

        return useCase.createWorkflow(workflowInpRequest);
    }
}
