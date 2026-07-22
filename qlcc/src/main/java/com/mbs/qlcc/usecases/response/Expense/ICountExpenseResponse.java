package com.mbs.qlcc.usecases.response.Expense;

import java.math.BigDecimal;

public interface ICountExpenseResponse {
    BigDecimal getPaid();

    BigDecimal getTotalExpect();
}
