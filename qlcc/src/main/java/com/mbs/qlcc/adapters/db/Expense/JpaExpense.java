package com.mbs.qlcc.adapters.db.Expense;

import com.mbs.qlcc.entities.Expense.Expense;
import com.mbs.qlcc.usecases.output.Expense.IExpenseDsGateway;
import com.mbs.qlcc.usecases.request.Expense.ExpenseFilterInpRequest;
import com.mbs.qlcc.usecases.response.Expense.ICountExpenseResponse;
import com.mbs.qlcc.usecases.response.Expense.IExpenseFilterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JpaExpense implements IExpenseDsGateway {
    private final JpaExpenseRepository repository;

    @Override
    public Map<String, Object> getByFilters(ExpenseFilterInpRequest request, String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size
        );

        Map<String, Object> params = new HashMap<>();

        if (request.getCategory() != null && !request.getCategory().isEmpty()) {
            params.put("category", request.getCategory());
        }

        if (request.getVendor() != null && !request.getVendor().isEmpty()) {
            params.put("vendor", request.getVendor());
        }

        if (request.getBuildingId() != null && !request.getBuildingId().isEmpty()) {
            params.put("buildingId", request.getBuildingId());
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            params.put("status", request.getStatus());
        }

        if (request.getApproved() != null) {
            params.put("approved", request.getApproved());
        }

        if (request.getProposedFrom() != null) {
            params.put("proposedFrom", request.getProposedFrom());
        }

        if (request.getProposedTo() != null) {
            params.put("proposedTo", request.getProposedTo());
        }

        Page<IExpenseFilterResponse> result = repository.getByFilters(
                complexId, params.get("category"), params.get("vendor"), params.get("status"),
                params.get("approved"), params.get("buildingId"),
                params.get("proposedFrom"), params.get("proposedTo"), pageable
        );

        ICountExpenseResponse count = repository.countByFilters(
                complexId, params.get("category"), params.get("vendor"), params.get("status"),
                params.get("approved"), params.get("buildingId"),
                params.get("proposedFrom"), params.get("proposedTo")
        );

        Map<String, Object> response = new HashMap<>();
        response.put("expenses", result);
        response.put("summary", count);

        return response;
    }

    @Override
    public void createExpense(List<Expense> expense) {
        List<ExpenseDataMapper> dataMappers = expense.stream()
                .map(this::mapToDataMapper)
                .toList();

        repository.saveAll(dataMappers);
    }

    @Override
    public List<Expense> updateExpense(List<Expense> expense) {
        List<ExpenseDataMapper> dataMappers = expense.stream()
                .map(this::mapToDataMapper)
                .toList();

        return repository.saveAll(dataMappers).stream()
                .map(this::mapToEntity)
                .toList();
    }


    @Override
    public Expense findExpenseById(String id) {
        return repository.findById(id)
                .map(this::mapToEntity)
                .orElse(null);
    }

    @Override
    public List<Expense> findExpensesByTaskId(String taskId) {
        return repository.findByTaskId(taskId).stream()
                .map(this::mapToEntity)
                .toList();
    }


    private Expense mapToEntity(ExpenseDataMapper dataMapper) {
        return new Expense(
                dataMapper.getId(),
                dataMapper.getTaskId(),
                dataMapper.getBuildingId(),
                dataMapper.getCategory(),
                dataMapper.getTitle(),
                dataMapper.getOriginalAmount(),
                dataMapper.getAmountPaid(),
                dataMapper.getVendor(),
                dataMapper.getStatus(),
                dataMapper.getDescription(),
                dataMapper.getCreatedBy(),
                dataMapper.getApprovedBy(),
                dataMapper.getApproved(),
                dataMapper.getApprovedAt(),
                dataMapper.getCreatedAt(),
                dataMapper.getUpdatedAt(),
                dataMapper.getDeletedAt(),
                dataMapper.isDeleted()
        );
    }

    private ExpenseDataMapper mapToDataMapper(Expense expense) {
        return ExpenseDataMapper.builder()
                .id(expense.getId())
                .taskId(expense.getTaskId())
                .buildingId(expense.getBuildingId())
                .category(expense.getCategory())
                .title(expense.getTitle())
                .originalAmount(expense.getOriginalAmount())
                .amountPaid(expense.getAmountPaid())
                .vendor(expense.getVendor())
                .status(expense.getStatus())
                .description(expense.getDescription())
                .createdBy(expense.getCreatedBy())
                .approvedBy(expense.getApprovedBy())
                .approved(expense.getApproved())
                .approvedAt(expense.getApprovedAt())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .deletedAt(expense.getDeletedAt())
                .isDeleted(expense.isDeleted())
                .build();
    }
}
