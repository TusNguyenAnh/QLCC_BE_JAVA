package com.mbs.qlcc.service.financial;

import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.LedgerSummary;
import com.mbs.qlcc.dto.request.FinancialModel.FinancialModelRequest;
import com.mbs.qlcc.dto.request.FinancialModel.RatioRequest;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.repository.Building.IBuildingRepository;
import com.mbs.qlcc.repository.Ledger.ILedgerSummaryRepository;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DecentralizedFinancialModelStrategy implements IFinancialModelStrategy {

    IBuildingRepository buildingRepository;
    ILedgerSummaryRepository ledgerSummaryRepository;

    @Override
    public String getModelType() {
        return Constant.DECENTRALIZED_FINANCIAL_MODEL.getValue();
    }

    @Override
    public void apply(Complex complex, FinancialModelRequest request) {
        if (request.getRatio() == null || request.getRatio().isEmpty()) {
            return;
        }

        List<String> buildingIds = request.getRatio().stream()
                .map(RatioRequest::getId)
                .collect(Collectors.toList());
        List<Building> buildings = buildingRepository.findAllByIdInAndComplexIdAndDeletedAtIsNull(buildingIds, complex.getId());

        if (buildings.size() != buildingIds.size()) {
            throw new AppException(ErrorCode.BUILDING_NOT_FOUND);
        }

        Float totalRatio = request.getRatio().stream()
                .map(RatioRequest::getRatio)
                .reduce(0f, Float::sum);

        if (Math.abs(totalRatio - 100f) > 0.001) {
            throw new AppException(ErrorCode.FINANCIAL_TOTAL_RATIO_NOT_VALID);
        }

        Map<String, Building> buildingMap = buildings.stream()
                .collect(Collectors.toMap(Building::getId, Function.identity()));

        request.getRatio().forEach((ratio) -> {
            Building building = buildingMap.get(ratio.getId());
            if (building != null) {
                building.setFinancialRatio(ratio.getRatio());
            }
        });

        buildingRepository.saveAll(buildings);

        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        List<LedgerSummary> ledgerSummaries = buildings.stream()
                .map(building -> LedgerSummary.builder()
                        .complexId(complex.getId())
                        .buildingId(building.getId())
                        .year(previousMonth.getYear())
                        .month(previousMonth.getMonthValue())
                        .totalIn(BigDecimal.ZERO)
                        .totalOut(BigDecimal.ZERO)
                        .openingBalance(BigDecimal.ZERO)
                        .closingBalance(BigDecimal.ZERO)
                        .isDeleted(false)
                        .build())
                .toList();

        ledgerSummaryRepository.saveAll(ledgerSummaries);
    }
}
