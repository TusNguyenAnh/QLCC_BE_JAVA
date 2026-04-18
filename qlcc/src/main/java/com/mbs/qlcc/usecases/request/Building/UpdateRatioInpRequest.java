package com.mbs.qlcc.usecases.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class UpdateRatioInpRequest {
    private String complexId;
    private List<RatioItem> ratios;

    public UpdateRatioInpRequest(String complexId, List<RatioItem> ratios) {
        this.complexId = complexId;
        this.ratios = ratios;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public List<RatioItem> getRatios() {
        return ratios;
    }

    public void setRatios(List<RatioItem> ratios) {
        this.ratios = ratios;
    }

    public static class RatioItem {
        private String id;
        private Float financialRatio;

        public RatioItem(String id, Float financialRatio) {
            this.id = id;
            this.financialRatio = financialRatio;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Float getFinancialRatio() {
            return financialRatio;
        }

        public void setFinancialRatio(Float financialRatio) {
            this.financialRatio = financialRatio;
        }
    }
}
