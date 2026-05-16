package com.mbs.qlcc.entities.FinancialModel;

import java.time.LocalDateTime;

public class FinancialModelFactory implements IFinancialModelFactory {
    @Override
    public FinancialModel createFinancialModel(String name, String type) {
        return new FinancialModel(name, type, false, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
