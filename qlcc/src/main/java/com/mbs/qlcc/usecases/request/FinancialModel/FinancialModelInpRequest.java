package com.mbs.qlcc.usecases.request.FinancialModel;

import java.util.Map;

public class FinancialModelInpRequest {
    private String type;
    private Map<String,Float> ratio;

    public FinancialModelInpRequest(String type, Map<String, Float> ratio) {
        this.type = type;
        this.ratio = ratio;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Float> getRatio() {
        return ratio;
    }

    public void setRatio(Map<String, Float> ratio) {
        this.ratio = ratio;
    }
}
