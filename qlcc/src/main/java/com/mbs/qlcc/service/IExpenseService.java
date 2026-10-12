package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Expense.ExpenseFilterRequest;
import com.mbs.qlcc.dto.request.Expense.ExpenseRequest;
import com.mbs.qlcc.dto.response.Expense.IExpenseFilterResponse;
import com.mbs.qlcc.dto.response.PageResponse;

import java.util.List;

public interface IExpenseService {
    void create(ExpenseRequest request, String complexId, String createdBy);
    PageResponse<IExpenseFilterResponse> getByFilter(ExpenseFilterRequest request, String complexId);
    List<IExpenseFilterResponse> getExpenseDetail(String expenseId, String complexId);
}
