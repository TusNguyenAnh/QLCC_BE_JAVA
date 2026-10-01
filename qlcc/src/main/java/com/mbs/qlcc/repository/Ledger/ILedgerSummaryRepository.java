package com.mbs.qlcc.repository.Ledger;

import com.mbs.qlcc.domain.LedgerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILedgerSummaryRepository extends JpaRepository<LedgerSummary, String> {

    Optional<LedgerSummary> findByComplexIdAndYearAndMonth(String complexId, Integer year, Integer month);

    Optional<LedgerSummary> findByComplexIdAndBuildingIdAndYearAndMonth(String complexId, String buildingId, Integer year, Integer month);

    @Query("SELECT ls FROM LedgerSummary ls WHERE ls.complexId = :complexId " +
            "AND ((ls.year < :year) " +
            "OR (ls.year = :year AND ls.month < :month)) " +
            "ORDER BY ls.year DESC, ls.month DESC " +
            "LIMIT 1 ")
    Optional<LedgerSummary> findNearestMonthToCurrentMonth(@Param("complexId") String complexId, @Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT ls FROM LedgerSummary ls WHERE ls.complexId = :complexId " +
            "AND ls.buildingId = :buildingId " +
            "AND ((ls.year < :year) " +
            "OR (ls.year = :year AND ls.month < :month)) " +
            "ORDER BY ls.year DESC, ls.month DESC " +
            "LIMIT 1 ")
    Optional<LedgerSummary> findNearestMonthAndBuildingToCurrentMonth(@Param("complexId") String complexId, @Param("buildingId") String buildingId, @Param("year") Integer year, @Param("month") Integer month);
}
