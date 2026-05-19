package com.mbs.qlcc.usecases.request.Workflow;

import java.util.List;

public class WorkflowStepInpRequest {
    private int orgLevel;
    private int stepOrder;
    private String description;
    private List<String> positions;

    public WorkflowStepInpRequest(int orgLevel, int stepOrder, String description, List<String> positions) {
        this.orgLevel = orgLevel;
        this.stepOrder = stepOrder;
        this.description = description;
        this.positions = positions;
    }

    public int getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(int orgLevel) {
        this.orgLevel = orgLevel;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }
}
