package com.mbs.qlcc.entities.Ledger;

import java.math.BigDecimal;

public interface ILedgerSummaryFactory {
    LedgerSummary createLedgerSummary(String complexId, String buildingId, Integer year, Integer month,
                                      BigDecimal totalIn, BigDecimal totalOut, BigDecimal openingBalance, BigDecimal closingBalance);
}
