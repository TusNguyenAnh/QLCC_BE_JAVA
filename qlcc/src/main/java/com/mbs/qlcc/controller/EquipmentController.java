package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.request.Equipment.CreateEquipmentRequest;
import com.mbs.qlcc.dto.request.Equipment.UpdateEquipmentRequest;
import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.dto.response.Equipment.EquipmentResponse;
import com.mbs.qlcc.dto.response.Equipment.IEquipmentHistoryResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.service.IEquipmentService;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentController {

    IEquipmentService equipmentService;

    @PostMapping
    public ApiResponse<EquipmentResponse> create(@RequestBody CreateEquipmentRequest request) {
        return ApiResponse.<EquipmentResponse>builder()
                .result(equipmentService.create(request, getCurrentComplexId()))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<EquipmentResponse> findById(@PathVariable String id) {
        return ApiResponse.<EquipmentResponse>builder()
                .result(equipmentService.findById(id, getCurrentComplexId()))
                .build();
    }

    @GetMapping("/history")
    public ApiResponse<List<IEquipmentHistoryResponse>> findHistory(@RequestParam(required = false) String id,
                                                                    @RequestParam(required = false) String category) {
        return ApiResponse.<List<IEquipmentHistoryResponse>>builder()
                .result(equipmentService.getEquipmentHistory(id, getCurrentComplexId(), category))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<EquipmentResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<EquipmentResponse>>builder()
                .result(equipmentService.search(getCurrentComplexId(), keyword, category, page, Math.min(size, 50)))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<EquipmentResponse> update(
            @PathVariable String id,
            @RequestBody UpdateEquipmentRequest request) {
        return ApiResponse.<EquipmentResponse>builder()
                .result(equipmentService.update(id, request, getCurrentComplexId()))
                .build();
    }

    @PostMapping("/delete")
    public ApiResponse<String> delete(@RequestBody List<String> ids) {
        equipmentService.delete(ids, getCurrentComplexId());
        return ApiResponse.<String>builder()
                .result("Equipments deleted successfully!")
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }
}
