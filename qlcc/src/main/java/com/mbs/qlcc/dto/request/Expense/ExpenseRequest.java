package com.mbs.qlcc.dto.request.Expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {
    private String taskId;
    /** Loại chi phí: "cum" (chi chung cụm) hoặc "toa" (chi theo tòa) */
    private String type;
    private String category;
    private BigDecimal totalAmount;
    private String description;
    private Instant payDate;
    private String equipmentId;
    /** Chỉ có giá trị khi type = "cum"; bỏ qua khi type = "toa" */
    List<ExpenseDetailRequest> expenseDetails;
}
