package com.mbs.qlcc.adapters.imports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentImportRow {
    private String buildingId;      // Tòa nhà
    private int floor;              // Tầng
    private String aptNumber;       // Số căn hộ
    private BigDecimal grossArea;       // Diện tích sàn
    private BigDecimal coefficient;     // Hệ số
    private String aptType;         // Loại căn hộ
    private String description;     // Mô tả
    private List<String> errors;    // Validation errors
}
