package com.mbs.qlcc.dto.request.Workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepRequest {
    private int orgLevel;
    private int stepOrder;
    private String description;
    private String moduleCode;
    private List<String> position;
}
