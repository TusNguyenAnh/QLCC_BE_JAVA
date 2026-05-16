package com.mbs.qlcc.adapters.db.Ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaLedgerSummaryRepository extends JpaRepository<LedgerSummaryDataMapper, String> {
    Optional<LedgerSummaryDataMapper> findByComplexIdAndYearAndMonth(String complexId, Integer year, Integer month);

    Optional<LedgerSummaryDataMapper> findByComplexIdAndBuildingIdAndYearAndMonth(String complexId, String buildingId, Integer year, Integer month);

    @Query("SELECT ls FROM LedgerSummaryDataMapper ls WHERE ls.complexId = :complexId " +
            "AND ((ls.year < :year) " +
            "OR (ls.year = :year AND ls.month < :month)) " +
            "ORDER BY ls.year DESC, ls.month DESC " +
            "LIMIT 1 ")
    Optional<LedgerSummaryDataMapper> findNearestMonthToCurrentMonth(String complexId, Integer year, Integer month);

    @Query("SELECT ls FROM LedgerSummaryDataMapper ls WHERE ls.complexId = :complexId " +
            "AND ls.buildingId = :buildingId " +
            "AND ((ls.year < :year) " +
            "OR (ls.year = :year AND ls.month < :month)) " +
            "ORDER BY ls.year DESC, ls.month DESC " +
            "LIMIT 1 ")
    Optional<LedgerSummaryDataMapper> findNearestMonthAndBuildingToCurrentMonth(String complexId, String buildingId, Integer year, Integer month);

}
