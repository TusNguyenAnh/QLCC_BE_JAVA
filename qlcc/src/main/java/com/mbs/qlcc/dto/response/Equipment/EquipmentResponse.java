package com.mbs.qlcc.dto.response.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentResponse {
    private String id;
    private String complexId;
    private String equipmentCode;
    private String equipmentName;
    private String category;
    private Instant operationDate;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}