package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Equipment.CreateEquipmentRequest;
import com.mbs.qlcc.dto.request.Equipment.UpdateEquipmentRequest;
import com.mbs.qlcc.dto.response.Equipment.EquipmentResponse;
import com.mbs.qlcc.dto.response.Equipment.IEquipmentHistoryResponse;
import com.mbs.qlcc.dto.response.PageResponse;

import java.util.List;

public interface IEquipmentService {
    EquipmentResponse create(CreateEquipmentRequest request, String complexId);

    EquipmentResponse findById(String id, String complexId);

    PageResponse<EquipmentResponse> search(String complexId, String keyword, String category, int page, int size);

    EquipmentResponse update(String id, UpdateEquipmentRequest request, String complexId);

    List<IEquipmentHistoryResponse> getEquipmentHistory(String id, String complexId, String category);

    void delete(List<String> ids, String complexId);
}
