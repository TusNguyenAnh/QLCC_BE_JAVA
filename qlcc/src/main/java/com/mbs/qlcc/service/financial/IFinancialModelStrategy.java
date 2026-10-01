package com.mbs.qlcc.service.financial;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.dto.request.FinancialModel.FinancialModelRequest;

public interface IFinancialModelStrategy {
    String getModelType();
    void apply(Complex complex, FinancialModelRequest request);
}
