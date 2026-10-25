package com.mbs.qlcc.mapper.Expense;

import com.mbs.qlcc.domain.Expense;
import com.mbs.qlcc.domain.ExpenseDetail;
import com.mbs.qlcc.dto.request.Expense.ExpenseDetailRequest;
import com.mbs.qlcc.dto.request.Expense.ExpenseRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapper {
    private ExpenseMapper() {
    }

    public static Expense toExpense(ExpenseRequest request, String complexId, String createdBy) {
        return Expense.builder()
                .taskId(request.getTaskId())
                .complexId(complexId)
                .equipmentId(request.getEquipmentId())
                .type(request.getType())
                .category(request.getCategory())
                .totalAmount(request.getTotalAmount())
                .description(request.getDescription())
                .payDate(request.getPayDate())
                .createdBy(createdBy)
                .build();
    }

    public static List<ExpenseDetail> toExpenseDetails(ExpenseRequest request, Expense expense) {
        if (request.getExpenseDetails() == null || request.getExpenseDetails().isEmpty()) {
            return Collections.emptyList();
        }
        return request.getExpenseDetails().stream()
                .map(detail -> toSingleExpenseDetail(detail, expense))
                .collect(Collectors.toList());
    }

    private static ExpenseDetail toSingleExpenseDetail(ExpenseDetailRequest detailRequest, Expense expense) {
        return ExpenseDetail.builder()
                .expense(expense)
                .buildingId(detailRequest.getBuildingId())
                .amount(detailRequest.getAmount())
                .build();
    }
}
