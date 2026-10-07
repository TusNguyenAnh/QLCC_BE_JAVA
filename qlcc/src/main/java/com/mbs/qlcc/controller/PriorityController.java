package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Priority.PriorityResponse;
import com.mbs.qlcc.service.IPriorityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/priority")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriorityController {
    IPriorityService priorityService;
    @GetMapping
    public ApiResponse<PageResponse<PriorityResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int perPage) {

        return ApiResponse.<PageResponse<PriorityResponse>>builder()
                .result(priorityService.show(page, Math.min(perPage, 50)))
                .build();
    }
}
