package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.FinancialModel.FinancialModelRequest;

public interface IFinancialModelService {
    String setFinancialModel(String complexId, FinancialModelRequest request);
}
