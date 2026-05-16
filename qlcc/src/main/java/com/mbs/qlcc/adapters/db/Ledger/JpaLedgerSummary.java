package com.mbs.qlcc.adapters.db.Ledger;

import com.mbs.qlcc.entities.Ledger.LedgerSummary;
import com.mbs.qlcc.usecases.output.Ledger.ILedgerSummaryDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaLedgerSummary implements ILedgerSummaryDsGateway {
    private final JpaLedgerSummaryRepository repository;

    @Override
    public void create(List<LedgerSummary> ledgerSummary) {
        List<LedgerSummaryDataMapper> dataMappers = ledgerSummary.stream()
                .map(this::mapToDataMapper)
                .toList();

        repository.saveAll(dataMappers);
    }

    @Override
    public void updateManySummary(List<LedgerSummary> ledgerSummary) {
        // lay ra cac ledger da co trong db
        // sau do map lai cac ledger moi vao data mapper, neu co id trung thi update, khong thi tao moi
    }

    @Override
    public LedgerSummary findByMonth(String complexId, Integer year, Integer month) {
        return repository.findByComplexIdAndYearAndMonth(complexId, year, month)
                .map(this::mapToEntity)
                .orElse(null);
    }

    @Override
    public LedgerSummary findByMonthAndBuilding(String complexId, String buildingId, Integer year, Integer month) {
        return repository.findByComplexIdAndBuildingIdAndYearAndMonth(complexId, buildingId, year, month)
                .map(this::mapToEntity)
                .orElse(null);
    }

    @Override
    public LedgerSummary findByMonthNearestToCurrentMonth(String complexId, Integer year, Integer month) {
        return repository.findNearestMonthToCurrentMonth(complexId, year, month)
                .map(this::mapToEntity)
                .orElse(null);
    }

    @Override
    public LedgerSummary findByMonthAndBuildingNearestToCurrentMonth(String complexId, String buildingId, Integer year, Integer month) {
        return repository.findNearestMonthAndBuildingToCurrentMonth(complexId, buildingId, year, month)
                .map(this::mapToEntity)
                .orElse(null);
    }

    private LedgerSummaryDataMapper mapToDataMapper(LedgerSummary ledgerSummary) {
        if (ledgerSummary == null) return null;
        return new LedgerSummaryDataMapper(
                ledgerSummary.getId(),
                ledgerSummary.getComplexId(),
                ledgerSummary.getBuildingId(),
                ledgerSummary.getYear(),
                ledgerSummary.getMonth(),
                ledgerSummary.getTotalIn(),
                ledgerSummary.getTotalOut(),
                ledgerSummary.getOpeningBalance(),
                ledgerSummary.getClosingBalance(),
                ledgerSummary.getCreatedAt(),
                ledgerSummary.getUpdatedAt(),
                ledgerSummary.getDeletedAt(),
                ledgerSummary.isDeleted()
        );
    }

    private LedgerSummary mapToEntity(LedgerSummaryDataMapper dataMapper) {
        if (dataMapper == null) return null;
        return new LedgerSummary(
                dataMapper.getId(),
                dataMapper.getComplexId(),
                dataMapper.getBuildingId(),
                dataMapper.getYear(),
                dataMapper.getMonth(),
                dataMapper.getTotalIn(),
                dataMapper.getTotalOut(),
                dataMapper.getOpeningBalance(),
                dataMapper.getClosingBalance()
        );
    }
}
