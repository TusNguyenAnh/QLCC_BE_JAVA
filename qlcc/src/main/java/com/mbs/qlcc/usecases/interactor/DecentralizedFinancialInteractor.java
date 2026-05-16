package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Building.Building;
import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.FinancialModel.FinancialModel;
import com.mbs.qlcc.entities.Ledger.ILedgerSummaryFactory;
import com.mbs.qlcc.entities.Ledger.LedgerSummary;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IFinancialModelInputBoundary;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.FinancialModel.IFinancialModelDsGateway;
import com.mbs.qlcc.usecases.output.Ledger.ILedgerSummaryDsGateway;
import com.mbs.qlcc.usecases.request.Building.UpdateRatioInpRequest;
import com.mbs.qlcc.usecases.request.FinancialModel.FinancialModelInpRequest;
import com.mbs.qlcc.utils.ErrorCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class DecentralizedFinancialInteractor implements IFinancialModelInputBoundary {
    private final IFinancialModelDsGateway financialModelDsGateway;
    private final IComplexDsGateway complexDsGateway;
    private final IBuildingDsGateway buildingDsGateway;
    private final ILedgerSummaryDsGateway ledgerSummaryDsGateway;
    private final ILedgerSummaryFactory ledgerSummaryFactory;

    public DecentralizedFinancialInteractor(IFinancialModelDsGateway financialModelDsGateway, IComplexDsGateway complexDsGateway, IBuildingDsGateway buildingDsGateway, ILedgerSummaryDsGateway ledgerSummaryDsGateway, ILedgerSummaryFactory ledgerSummaryFactory) {
        this.financialModelDsGateway = financialModelDsGateway;
        this.complexDsGateway = complexDsGateway;
        this.buildingDsGateway = buildingDsGateway;
        this.ledgerSummaryDsGateway = ledgerSummaryDsGateway;
        this.ledgerSummaryFactory = ledgerSummaryFactory;
    }

    @Override
    public void setFinancialModel(String complexId, FinancialModelInpRequest request) {
        if (!request.getType().isEmpty()) {
            FinancialModel finanType = financialModelDsGateway.getFinancialModelByName(request.getType());
            if (finanType == null) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            Complex complex = complexDsGateway.findById(complexId)
                    .orElseThrow(() -> new AppException(ErrorCode.COMPLEX_NOT_FOUND));

            complex.setFinancialModel(finanType.getType());
            complexDsGateway.save(complex);
        }

        if (!request.getRatio().isEmpty()) {
            List<String> buildingIds = request.getRatio().keySet().stream().toList();
            List<Building> buildings = buildingDsGateway.validateBuildingIds(buildingIds, complexId);

            if (buildings.size() != buildingIds.size()) {
                throw new AppException(ErrorCode.BUILDING_NOT_FOUND);
            }

            Float totalRatio = request.getRatio().values().stream()
                    .reduce(0f, Float::sum);

            if (Math.abs(totalRatio - 100f) > 0.001) {  // Allow small floating point errors
                throw new AppException(ErrorCode.FINANCIAL_TOTAL_RATIO_NOT_VALID);
            }

            request.getRatio().forEach((buildingId, ratio) -> buildingDsGateway.updateFinancialRatio(buildingId, ratio));

            if (!buildings.isEmpty()) {
                LocalDate previousMonth = LocalDate.now().minusMonths(1);
                List<LedgerSummary> ledgerSummary = buildings.stream()
                        .map(building -> ledgerSummaryFactory.createLedgerSummary(
                                complexId, building.getId(),
                                previousMonth.getYear(), previousMonth.getMonthValue(),
                                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
                        ))
                        .toList();


                ledgerSummaryDsGateway.create(ledgerSummary);
            }
        }

    }

    @Override
    public String type() {
        return "decentralized";
    }
}
