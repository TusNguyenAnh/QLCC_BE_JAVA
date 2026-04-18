package com.mbs.qlcc.adapters.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRatioRequest {
    private String complexId;
    private List<RatioItem> ratios;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RatioItem {
        private String id;
        private Float financialRatio;
    }
}
