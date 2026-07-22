package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.Revenue.CreateRevenueInpRequest;
import com.mbs.qlcc.usecases.request.Revenue.RevenueFilterInpRequest;

import java.util.Map;

public interface IRevenueInputBoundary {
    void createRevenue(CreateRevenueInpRequest request);

    Map<String, Object> filterRevenue(RevenueFilterInpRequest request, String complexId, int page, int size);

    String type();
}
