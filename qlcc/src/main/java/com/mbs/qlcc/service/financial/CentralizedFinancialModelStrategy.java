package com.mbs.qlcc.service.financial;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.LedgerSummary;
import com.mbs.qlcc.dto.request.FinancialModel.FinancialModelRequest;
import com.mbs.qlcc.repository.Ledger.ILedgerSummaryRepository;
import com.mbs.qlcc.utils.Constant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CentralizedFinancialModelStrategy implements IFinancialModelStrategy {

    ILedgerSummaryRepository ledgerSummaryRepository;

    @Override
    public String getModelType() {
        return Constant.CENTRALIZED_FINANCIAL_MODEL.getValue();
    }

    @Override
    public void apply(Complex complex, FinancialModelRequest request) {
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        LedgerSummary ledgerSummary = LedgerSummary.builder()
                .complexId(complex.getId())
                .buildingId(null)
                .year(previousMonth.getYear())
                .month(previousMonth.getMonthValue())
                .totalIn(BigDecimal.ZERO)
                .totalOut(BigDecimal.ZERO)
                .openingBalance(BigDecimal.ZERO)
                .closingBalance(BigDecimal.ZERO)
                .isDeleted(false)
                .build();

        ledgerSummaryRepository.save(ledgerSummary);
    }
}
