package com.mbs.qlcc.mapper.Equipment;

import com.mbs.qlcc.domain.Equipment;
import com.mbs.qlcc.dto.request.Equipment.CreateEquipmentRequest;
import com.mbs.qlcc.dto.response.Equipment.EquipmentResponse;

import java.time.Instant;
import java.time.ZoneId;

public class EquipmentMapper {

    public static EquipmentResponse toResponse(Equipment equipment) {
        if (equipment == null) return null;

        return EquipmentResponse.builder()
                .id(equipment.getId())
                .complexId(equipment.getComplexId())
                .equipmentCode(equipment.getEquipmentCode())
                .equipmentName(equipment.getEquipmentName())
                .category(equipment.getCategory())
                .operationDate(equipment.getOperationDate())
                .description(equipment.getDescription())
                .createdAt(equipment.getCreatedAt())
                .updatedAt(equipment.getUpdatedAt())
                .build();
    }

    public static Equipment toEntity(CreateEquipmentRequest request, String complexId) {
        if (request == null) return null;
        Instant instant = request.getOperationDate()
                .atZone(ZoneId.systemDefault())
                .toInstant();

        return Equipment.builder()
                .complexId(complexId)
                .equipmentCode(request.getEquipmentCode().trim())
                .equipmentName(request.getEquipmentName().trim())
                .category(request.getCategory().trim())
                .operationDate(instant)
                .description(request.getDescription())
                .build();
    }

    private EquipmentMapper() {
    }
}