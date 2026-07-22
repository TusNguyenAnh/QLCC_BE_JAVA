package com.mbs.qlcc.usecases.output.Expense;

import com.mbs.qlcc.entities.Expense.Expense;
import com.mbs.qlcc.usecases.request.Expense.ExpenseFilterInpRequest;

import java.util.List;
import java.util.Map;

public interface IExpenseDsGateway {
    Map<String, Object> getByFilters(ExpenseFilterInpRequest request, String complexId, int page, int size);

    void createExpense(List<Expense> expense);

    List<Expense> updateExpense(List<Expense> expense);

    Expense findExpenseById(String id);

    List<Expense> findExpensesByTaskId(String taskId);
}
