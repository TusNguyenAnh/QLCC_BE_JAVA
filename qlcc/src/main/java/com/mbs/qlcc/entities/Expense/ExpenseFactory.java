package com.mbs.qlcc.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseFactory implements IExpenseFactory {
    @Override
    public Expense createExpense(String taskId, String buildingId, String category, String title, BigDecimal originalAmount, BigDecimal amountPaid, String vendor, String status, String description, String createdBy, String approvedBy, Integer approved, LocalDateTime approvedAt) {
        return new Expense(taskId, buildingId, category, title, originalAmount, amountPaid, vendor, status, description, createdBy, approvedBy, approved, approvedAt, LocalDateTime.now(), LocalDateTime.now(), null, false);
    }
}
