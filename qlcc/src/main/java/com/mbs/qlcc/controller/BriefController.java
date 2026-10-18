package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Brief.CreateBriefRequest;
import com.mbs.qlcc.dto.request.Brief.UpdateBriefRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.Brief.BriefResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.service.IBriefService;
import com.mbs.qlcc.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/briefs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Brief Management", description = "APIs for Brief module (hồ sơ tài liệu)")
public class BriefController {

    IBriefService briefService;

    @Operation(summary = "Tạo mới hồ sơ (brief)")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<BriefResponse> create(
            @ModelAttribute @Valid CreateBriefRequest request
    ) throws IOException {
        String complexId = getCurrentComplexId();
        return ApiResponse.<BriefResponse>builder()
                .result(briefService.create(request, complexId))
                .build();
    }

    @Operation(summary = "Cập nhật tên file hoặc loại hồ sơ")
    @PutMapping("/{id}")
    public ApiResponse<BriefResponse> update(
            @PathVariable String id,
            @RequestBody @Valid UpdateBriefRequest request
    ) {
        String complexId = getCurrentComplexId();
        return ApiResponse.<BriefResponse>builder()
                .result(briefService.update(id, request, complexId))
                .build();
    }

    @Operation(summary = "Lấy chi tiết hồ sơ theo ID")
    @GetMapping("/{id}")
    public ApiResponse<BriefResponse> findById(
            @PathVariable String id
    ) {
        String complexId = getCurrentComplexId();
        return ApiResponse.<BriefResponse>builder()
                .result(briefService.findById(id, complexId))
                .build();
    }

    @Operation(summary = "Lọc danh sách hồ sơ kết hợp tên file và loại")
    @GetMapping
    public ApiResponse<PageResponse<BriefResponse>> filter(
            @RequestParam(required = false) String briefName,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        String complexId = getCurrentComplexId();

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ApiResponse.<PageResponse<BriefResponse>>builder()
                .result(briefService.filter(complexId, briefName, type, pageable))
                .build();
    }

    @Operation(summary = "Xóa mềm hồ sơ và xóa file trong disk")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @PathVariable String id
    ) {
        String complexId = getCurrentComplexId();
        briefService.delete(id, complexId);

        return ApiResponse.<String>builder()
                .result("Brief deleted successfully!")
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }
}
