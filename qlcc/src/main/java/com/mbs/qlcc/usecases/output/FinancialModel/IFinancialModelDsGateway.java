package com.mbs.qlcc.usecases.output.FinancialModel;

import com.mbs.qlcc.entities.FinancialModel.FinancialModel;

import java.util.List;

public interface IFinancialModelDsGateway {
    List<FinancialModel> getFinancialModels();
    FinancialModel getFinancialModelById(String id);
    FinancialModel getFinancialModelByName(String name);
}
