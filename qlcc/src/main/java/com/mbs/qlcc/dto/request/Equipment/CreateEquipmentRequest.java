package com.mbs.qlcc.dto.request.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEquipmentRequest {
    private String equipmentCode;
    private String equipmentName;
    private String category;
    private LocalDateTime operationDate;
    private String description;
}
