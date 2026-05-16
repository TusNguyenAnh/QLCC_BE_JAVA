package com.mbs.qlcc.usecases.output.Ledger;

import com.mbs.qlcc.entities.Ledger.LedgerSummary;

import java.util.List;

public interface ILedgerSummaryDsGateway {
    void create(List<LedgerSummary> ledgerSummary);

    void updateManySummary(List<LedgerSummary> ledgerSummary);

    LedgerSummary findByMonth(String complexId, Integer year, Integer month);

    LedgerSummary findByMonthAndBuilding(String complexId, String buildingId, Integer year, Integer month);

    LedgerSummary findByMonthNearestToCurrentMonth(String complexId, Integer year, Integer month);

    LedgerSummary findByMonthAndBuildingNearestToCurrentMonth(String complexId, String buildingId, Integer year, Integer month);

}
