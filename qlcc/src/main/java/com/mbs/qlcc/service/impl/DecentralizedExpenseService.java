package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Expense;
import com.mbs.qlcc.domain.ExpenseDetail;
import com.mbs.qlcc.dto.request.Expense.ExpenseFilterRequest;
import com.mbs.qlcc.dto.request.Expense.ExpenseRequest;
import com.mbs.qlcc.dto.response.Expense.IExpenseFilterResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.mapper.Expense.ExpenseMapper;
import com.mbs.qlcc.repository.Expense.IExpenseDetailRepository;
import com.mbs.qlcc.repository.Expense.IExpenseRepository;
import com.mbs.qlcc.service.IExpenseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DecentralizedExpenseService implements IExpenseService {

    static final String TYPE_CUM = "cum";
    static final String TYPE_TOA = "toa";

    IExpenseRepository expenseRepository;
    IExpenseDetailRepository expenseDetailRepository;

    @Override
    @Transactional
    public void create(ExpenseRequest request, String complexId, String createdBy) {
        // 1. Persist Expense trước để có id
        Expense expense = ExpenseMapper.toExpense(request, complexId, createdBy);
        expense = expenseRepository.save(expense);

        // 2. Tạo và lưu ExpenseDetail dựa theo type
        List<ExpenseDetail> details = ExpenseMapper.toExpenseDetails(request, expense);
        if (!details.isEmpty()) {
            expenseDetailRepository.saveAll(details);
        }
    }

    @Override
    public PageResponse<IExpenseFilterResponse> getByFilter(ExpenseFilterRequest request, String complexId) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());

        // Chuẩn bị tham số nullable cho repository
        Object buildingIds   = toNullableParam(request.getBuildingId());
        Object categories    = toNullableParam(request.getCategory());
        Object proposedFrom  = request.getProposedFrom();
        Object proposedTo    = request.getProposedTo();
        String checkCategory = (request.getCategory() == null || request.getCategory().isEmpty())
                ? null : "hasCategory";

        Page<IExpenseFilterResponse> page;

        if (TYPE_CUM.equalsIgnoreCase(request.getType())) {
            // buildingCount = số tòa trong list → dùng để HAVING COUNT(DISTINCT ed.building_id) = :buildingCount
            int buildingCount = (request.getBuildingId() != null) ? request.getBuildingId().size() : 0;
            page = expenseRepository.getByCluster(
                    complexId,
                    checkCategory,
                    request.getType(),
                    buildingIds,
                    buildingCount,
                    categories,
                    proposedFrom,
                    proposedTo,
                    pageable);
        } else {
            // Mặc định xử lý theo tòa (type = "toa")
            page = expenseRepository.getByBuilding(
                    complexId,
                    checkCategory,
                    buildingIds,
                    categories,
                    proposedFrom,
                    proposedTo,
                    pageable);
        }

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }


    @Override
    public List<IExpenseFilterResponse> getExpenseDetail(String expenseId, String complexId) {
        return expenseRepository.getExpenseDetail(complexId, expenseId);
    }

    private Object toNullableParam(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.singletonList("__NONE__"); // dummy value tránh IN() rỗng
        }
        return list;
    }
}
