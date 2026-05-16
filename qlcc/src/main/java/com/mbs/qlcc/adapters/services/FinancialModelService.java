package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.factory.FinancialFactory;
import com.mbs.qlcc.adapters.request.FinancialModel.FinancialModelRequest;
import com.mbs.qlcc.usecases.input.IFinancialModelInputBoundary;
import com.mbs.qlcc.usecases.request.FinancialModel.FinancialModelInpRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinancialModelService {
    FinancialFactory factory;

    public String setFinancialModel(String complexId, FinancialModelRequest request) {
        FinancialModelInpRequest inpRequest = new FinancialModelInpRequest(request.getType(), request.getRatio());
        IFinancialModelInputBoundary usecase = factory.get(request.getType());
        usecase.setFinancialModel(complexId, inpRequest);
        return "Financial model set successfully";
    }
}
