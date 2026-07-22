package com.mbs.qlcc.usecases.output.Revenue;

import com.mbs.qlcc.entities.Revenue.Revenue;
import com.mbs.qlcc.usecases.request.Revenue.RevenueFilterInpRequest;

import java.util.List;
import java.util.Map;

public interface IRevenueDsGateway {
    void createRevenue(List<Revenue> revenue);

    List<Revenue> updateRevenue(List<Revenue> revenue);

    Revenue findRevenueById(String id);

    List<Revenue> findRevenuesByTaskId(String taskId);

    Map<String, Object> getByFilters(RevenueFilterInpRequest request, String complexId, int page, int size);
}
