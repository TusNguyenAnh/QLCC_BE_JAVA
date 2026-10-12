package com.mbs.qlcc.dto.request.Expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseFilterRequest {
    private List<String> buildingId;
    private List<String> category;
    private LocalDateTime proposedFrom;
    private LocalDateTime proposedTo;
    private String type;
    int pageNumber;
    int pageSize;
}
