package com.mbs.qlcc.dto.response.Expense;

import java.math.BigDecimal;
import java.time.Instant;

public interface IExpenseFilterResponse {
    String getId();

    String getCategory();

    BigDecimal getAmount();

    String getDescription();

    Instant getPayDate();
    String getTaskId();
}
