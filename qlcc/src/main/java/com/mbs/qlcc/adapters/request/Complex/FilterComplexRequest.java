package com.mbs.qlcc.adapters.request.Complex;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Filter Complex Request DTO (From Client)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterComplexRequest {
    @Null
    String keyword;
    @Null
    LocalDateTime timeRequestStart;
    @Null
    LocalDateTime timeRequestEnd;
    @Null
    String order;  // asc or desc
    int pageNumber;
    int pageSize;
}
