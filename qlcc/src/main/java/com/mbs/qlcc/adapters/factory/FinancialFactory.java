package com.mbs.qlcc.adapters.factory;

import com.mbs.qlcc.usecases.input.IFinancialModelInputBoundary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FinancialFactory {
    private final Map<String, IFinancialModelInputBoundary> gateways;
    public FinancialFactory(List<IFinancialModelInputBoundary> list) {
        gateways = list.stream()
                .collect(Collectors.toMap(
                        IFinancialModelInputBoundary::type,
                        Function.identity()
                ));
    }

    public IFinancialModelInputBoundary get(String type) {
        return gateways.get(type);
    }
}
