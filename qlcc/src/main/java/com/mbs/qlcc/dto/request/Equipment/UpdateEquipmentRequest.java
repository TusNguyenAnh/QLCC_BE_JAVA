package com.mbs.qlcc.dto.request.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEquipmentRequest {
    private String equipmentCode;
    private String equipmentName;
    private String category;
    private String description;
}

