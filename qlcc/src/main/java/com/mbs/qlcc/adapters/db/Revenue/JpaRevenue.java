package com.mbs.qlcc.adapters.db.Revenue;

import com.mbs.qlcc.entities.Revenue.Revenue;
import com.mbs.qlcc.usecases.output.Revenue.IRevenueDsGateway;
import com.mbs.qlcc.usecases.request.Revenue.RevenueFilterInpRequest;
import com.mbs.qlcc.usecases.response.Revenue.ICountRevenueResponse;
import com.mbs.qlcc.usecases.response.Revenue.IRevenueFilterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JpaRevenue implements IRevenueDsGateway {
    private final JpaRevenueRepository repository;

    @Override
    public void createRevenue(List<Revenue> revenue) {
        List<RevenueDataMapper> dataMappers = revenue.stream()
                .map(this::mapToRevenueDataMapper)
                .toList();

        repository.saveAll(dataMappers);
    }

    @Override
    public List<Revenue> updateRevenue(List<Revenue> revenue) {
        List<RevenueDataMapper> dataMappers = revenue.stream()
                .map(this::mapToRevenueDataMapper)
                .toList();

        List<RevenueDataMapper> updatedDataMappers = repository.saveAll(dataMappers);

        return updatedDataMappers.stream()
                .map(this::mapToRevenue)
                .toList();
    }


    @Override
    public Revenue findRevenueById(String id) {
        return repository.findById(id)
                .map(this::mapToRevenue)
                .orElse(null);
    }

    @Override
    public List<Revenue> findRevenuesByTaskId(String taskId) {
        return repository.findByTaskId(taskId).stream()
                .map(this::mapToRevenue)
                .toList();
    }

    @Override
    public Map<String, Object> getByFilters(RevenueFilterInpRequest request, String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "r.approved_at"
        );

        Map<String, Object> params = new HashMap<>();
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

        Page<IRevenueFilterResponse> result = repository.getByFilters(
                complexId, params.get("status"),
                params.get("approved"), params.get("buildingId"),
                params.get("proposedFrom"), params.get("proposedTo"), pageable
        );

        ICountRevenueResponse count = repository.countByFilters(
                complexId, params.get("status"),
                params.get("approved"), params.get("buildingId"),
                params.get("proposedFrom"), params.get("proposedTo")
        );

        Map<String, Object> response = new HashMap<>();
        response.put("revenues", result);
        response.put("summary", count);

        return response;
    }

    private Revenue mapToRevenue(RevenueDataMapper mapper) {
        return new Revenue(
                mapper.getId(),
                mapper.getTaskId(),
                mapper.getBuildingId(),
                mapper.getApartmentId(),
                mapper.getTitle(),
                mapper.getOriginalAmount(),
                mapper.getAmountPaid(),
                mapper.getStatus(),
                mapper.getDescription(),
                mapper.getCreatedBy(),
                mapper.getApprovedBy(),
                mapper.getApproved(),
                mapper.getApprovedAt(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt(),
                mapper.isDeleted()
        );
    }

    private RevenueDataMapper mapToRevenueDataMapper(Revenue revenue) {
        return RevenueDataMapper.builder()
                .id(revenue.getId())
                .taskId(revenue.getTaskId())
                .buildingId(revenue.getBuildingId())
                .apartmentId(revenue.getApartmentId())
                .title(revenue.getTitle())
                .originalAmount(revenue.getOriginalAmount())
                .amountPaid(revenue.getAmountPaid())
                .status(revenue.getStatus())
                .description(revenue.getDescription())
                .createdBy(revenue.getCreatedBy())
                .approvedBy(revenue.getApprovedBy())
                .approved(revenue.getApproved())
                .approvedAt(revenue.getApprovedAt())
                .createdAt(revenue.getCreatedAt())
                .updatedAt(revenue.getUpdatedAt())
                .deletedAt(revenue.getDeletedAt())
                .isDeleted(revenue.isDeleted())
                .build();
    }
}
