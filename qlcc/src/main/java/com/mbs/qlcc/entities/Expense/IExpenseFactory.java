package com.mbs.qlcc.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IExpenseFactory {
    Expense createExpense(String taskId, String buildingId, String category, String title, BigDecimal originalAmount, BigDecimal amountPaid,
                          String vendor, String status, String description, String createdBy, String approvedBy, Integer approved, LocalDateTime approvedAt);
}
