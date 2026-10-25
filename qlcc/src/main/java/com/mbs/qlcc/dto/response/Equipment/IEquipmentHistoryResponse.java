package com.mbs.qlcc.dto.response.Equipment;

import java.math.BigDecimal;
import java.time.Instant;

public interface IEquipmentHistoryResponse {
    Instant getExpenseDate();
    String getDescription();
    BigDecimal getAmount();
}
