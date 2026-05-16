package com.mbs.qlcc.entities.Ledger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LedgerSummaryFactory implements ILedgerSummaryFactory {
    @Override
    public LedgerSummary createLedgerSummary(String complexId, String buildingId, Integer year, Integer month,
                                             BigDecimal totalIn, BigDecimal totalOut, BigDecimal openingBalance, BigDecimal closingBalance) {
        return new LedgerSummary(complexId, buildingId, year, month, totalIn, totalOut, openingBalance, closingBalance,
                LocalDateTime.now(), LocalDateTime.now(), null, false);
    }
}
