package com.mbs.qlcc.adapters.request.Apartment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterApartmentRequest {
    private String keyword;
    private LocalDateTime timeRequestStart;
    private LocalDateTime timeRequestEnd;
    private String order;
    private int pageNumber;
    private int pageSize;
}
