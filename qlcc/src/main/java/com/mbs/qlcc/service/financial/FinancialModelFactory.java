package com.mbs.qlcc.service.financial;

import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.utils.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FinancialModelFactory {

    private final Map<String, IFinancialModelStrategy> strategies;

    public FinancialModelFactory(List<IFinancialModelStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getModelType().toLowerCase(),
                        Function.identity()
                ));
    }

    public IFinancialModelStrategy getStrategy(String type) {
        if (type == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        IFinancialModelStrategy strategy = strategies.get(type.toLowerCase());
        if (strategy == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return strategy;
    }
}
