package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Expense.ExpenseFilterRequest;
import com.mbs.qlcc.dto.request.Expense.ExpenseRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Expense.IExpenseFilterResponse;
import com.mbs.qlcc.service.IExpenseService;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseController {

    IExpenseService expenseService;
    @PostMapping
    public ApiResponse<String> create(@RequestBody ExpenseRequest request) {
        String complexId = getCurrentComplexId();
        String userId = getCurrentUserId();
        expenseService.create(request, complexId, userId);
        return ApiResponse.<String>builder()
                .result("Expense created successfully!")
                .build();
    }
    @PostMapping("/filter/{complexId}")
    public ApiResponse<PageResponse<IExpenseFilterResponse>> getByFilter(
            @RequestBody ExpenseFilterRequest request,
            @PathVariable String complexId) {
        return ApiResponse.<PageResponse<IExpenseFilterResponse>>builder()
                .result(expenseService.getByFilter(request, complexId))
                .build();
    }
    @GetMapping("/{expenseId}/detail/{complexId}")
    public ApiResponse<List<IExpenseFilterResponse>> getExpenseDetail(
            @PathVariable String expenseId,
            @PathVariable String complexId) {
        return ApiResponse.<List<IExpenseFilterResponse>>builder()
                .result(expenseService.getExpenseDetail(expenseId, complexId))
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }

    private String getCurrentUserId() {
        return JwtUtil.getUserId(JwtUtil.getToken());
    }
}
