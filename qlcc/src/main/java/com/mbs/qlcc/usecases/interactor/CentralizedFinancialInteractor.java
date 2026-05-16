package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.FinancialModel.FinancialModel;
import com.mbs.qlcc.entities.Ledger.ILedgerSummaryFactory;
import com.mbs.qlcc.entities.Ledger.LedgerSummary;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IFinancialModelInputBoundary;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.FinancialModel.IFinancialModelDsGateway;
import com.mbs.qlcc.usecases.output.Ledger.ILedgerSummaryDsGateway;
import com.mbs.qlcc.usecases.request.FinancialModel.FinancialModelInpRequest;
import com.mbs.qlcc.utils.ErrorCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CentralizedFinancialInteractor implements IFinancialModelInputBoundary {
    private final IFinancialModelDsGateway financialModelGateway;
    private final IComplexDsGateway complexGateway;
    private final ILedgerSummaryDsGateway ledgerSummaryGateway;
    private final ILedgerSummaryFactory ledgerSummaryFactory;

    public CentralizedFinancialInteractor(IFinancialModelDsGateway financialModelGateway, IComplexDsGateway complexGateway, ILedgerSummaryDsGateway ledgerSummaryGateway, ILedgerSummaryFactory ledgerSummaryFactory) {
        this.financialModelGateway = financialModelGateway;
        this.complexGateway = complexGateway;
        this.ledgerSummaryGateway = ledgerSummaryGateway;
        this.ledgerSummaryFactory = ledgerSummaryFactory;
    }

    @Override // test xem co update complex k?
    public void setFinancialModel(String complexId, FinancialModelInpRequest request) {
        if (!request.getType().isEmpty()) {
            FinancialModel finanType = financialModelGateway.getFinancialModelByName(request.getType());
            if (finanType == null) {
                throw new AppException(ErrorCode.NOT_FOUND);
            }

            Complex complex = complexGateway.findById(complexId)
                    .orElseThrow(() -> new AppException(ErrorCode.COMPLEX_NOT_FOUND));

            complex.setFinancialModel(finanType.getType());
            complexGateway.save(complex);

            LocalDate previousMonth = LocalDate.now().minusMonths(1);
            List<LedgerSummary> ledgerSummary = List.of(ledgerSummaryFactory.createLedgerSummary(
                    complexId, null,
                    previousMonth.getYear(), previousMonth.getMonthValue(),
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

            ledgerSummaryGateway.create(ledgerSummary);
        }

    }

    @Override
    public String type() {
        return "centralized";
    }
}
