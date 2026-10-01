package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.FinancialModel;
import com.mbs.qlcc.dto.request.FinancialModel.FinancialModelRequest;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.repository.Complex.IComplexRepository;
import com.mbs.qlcc.repository.FinancialModel.IFinancialModelRepository;
import com.mbs.qlcc.service.IFinancialModelService;
import com.mbs.qlcc.service.financial.FinancialModelFactory;
import com.mbs.qlcc.service.financial.IFinancialModelStrategy;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinancialModelServiceImpl implements IFinancialModelService {

    IFinancialModelRepository financialModelRepository;
    IComplexRepository complexRepository;
    FinancialModelFactory financialModelFactory;

    @Override
    @Transactional
    public String setFinancialModel(String complexId, FinancialModelRequest request) {
        if (request.getType() == null || request.getType().isBlank()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        FinancialModel financialModel = financialModelRepository.findByType(request.getType())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        Complex complex = complexRepository.findById(complexId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPLEX_NOT_FOUND));

        complex.setFinancialModel(financialModel.getType());
        complexRepository.save(complex);

        IFinancialModelStrategy strategy = financialModelFactory.getStrategy(financialModel.getType());
        strategy.apply(complex, request);

        return "Financial model set successfully";
    }
}
