package com.mbs.qlcc.dto.response.Expense;

import java.math.BigDecimal;

public interface ICountExpenseResponse {
    BigDecimal getPaid();

    BigDecimal getTotalExpect();
}
