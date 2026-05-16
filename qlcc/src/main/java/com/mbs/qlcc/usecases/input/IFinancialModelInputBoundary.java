package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.FinancialModel.FinancialModelInpRequest;

public interface IFinancialModelInputBoundary {
    void setFinancialModel(String complexId, FinancialModelInpRequest request);
    String type();
}
