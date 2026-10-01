package com.mbs.qlcc.dto.response.Revenue;

import java.math.BigDecimal;

public interface ICountRevenueResponse {
    BigDecimal getPaid();

    BigDecimal getTotalExpect();
}
