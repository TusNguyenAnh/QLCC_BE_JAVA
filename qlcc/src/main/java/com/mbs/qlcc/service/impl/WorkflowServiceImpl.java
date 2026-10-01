package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Workflow;
import com.mbs.qlcc.domain.WorkflowStep;
import com.mbs.qlcc.domain.WorkflowStepApprover;
import com.mbs.qlcc.dto.request.Workflow.WorkflowRequest;
import com.mbs.qlcc.dto.request.Workflow.WorkflowStepRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Workflow.WorkflowResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.repository.Role.IRoleRepository;
import com.mbs.qlcc.repository.Workflow.IWorkflowRepository;
import com.mbs.qlcc.repository.Workflow.IWorkflowStepApproverRepository;
import com.mbs.qlcc.repository.Workflow.IWorkflowStepRepository;
import com.mbs.qlcc.service.IWorkflowService;
import com.mbs.qlcc.utils.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkflowServiceImpl implements IWorkflowService {

    IWorkflowRepository workflowRepository;
    IWorkflowStepRepository workflowStepRepository;
    IWorkflowStepApproverRepository workflowStepApproverRepository;
    IRoleRepository roleRepository;

    @Override
    public PageResponse<WorkflowResponse> getByComplexId(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<WorkflowResponse> result = workflowRepository.findByComplexIdAndDeletedAtIsNull(complexId, pageable);
        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    @Transactional
    public String createWorkflow(String complexId, WorkflowRequest request) {
        try {
            Workflow workflow = Workflow.builder()
                    .complexId(complexId)
                    .workflowName(request.getWorkflowName())
                    .description(request.getDescription())
                    .status(0)
                    .build();
            Workflow savedWorkflow = workflowRepository.save(workflow);

            if (request.getWorkflowSteps() != null && !request.getWorkflowSteps().isEmpty()) {
                List<WorkflowStep> steps = new ArrayList<>();
                List<WorkflowStepApprover> approvers = new ArrayList<>();

                for (WorkflowStepRequest stepReq : request.getWorkflowSteps()) {
                    String stepId = UUID.randomUUID().toString();

                    WorkflowStep step = WorkflowStep.builder()
                            .id(stepId)
                            .workflow(savedWorkflow)
                            .stepOrder(stepReq.getStepOrder()) // neu step truyen len khong la so ?
                            .orgLevel(stepReq.getOrgLevel()) // neu cap bqt khong co trong org ?
                            .description(stepReq.getDescription())
                            .moduleCode(stepReq.getModuleCode())
                            .status(0)
                            .build();
                    steps.add(step);

                    if (stepReq.getPosition() != null && !stepReq.getPosition().isEmpty()) {
                        for (String roleId : stepReq.getPosition()) {
                            WorkflowStepApprover approver = WorkflowStepApprover.builder()
                                    .workflowStep(step)
                                    .role(roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)))
                                    .status(0)
                                    .build();
                            approvers.add(approver);
                        }
                    }
                }

                workflowStepRepository.saveAll(steps);
                workflowStepRepository.flush();
                workflowStepApproverRepository.saveAll(approvers);
            }

            return "Create workflow successfully";
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_CREATED);
        }
    }
}
