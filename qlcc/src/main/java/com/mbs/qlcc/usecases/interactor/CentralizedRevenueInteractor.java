package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.usecases.input.IRevenueInputBoundary;
import com.mbs.qlcc.usecases.request.Revenue.CreateRevenueInpRequest;
import com.mbs.qlcc.usecases.request.Revenue.RevenueFilterInpRequest;

import java.util.Map;

public class CentralizedRevenueInteractor implements IRevenueInputBoundary {
    @Override
    public void createRevenue(CreateRevenueInpRequest request) {

    }

    @Override
    public Map<String, Object> filterRevenue(RevenueFilterInpRequest request, String complexId, int page, int size) {
        return null;
    }

    @Override
    public String type() {
        return "centralized";
    }

}
