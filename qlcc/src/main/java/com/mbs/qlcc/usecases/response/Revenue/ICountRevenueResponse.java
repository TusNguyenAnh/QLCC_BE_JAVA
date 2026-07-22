package com.mbs.qlcc.usecases.response.Revenue;

import java.math.BigDecimal;

public interface ICountRevenueResponse {
    BigDecimal getPaid();

    BigDecimal getTotalExpect();
}
