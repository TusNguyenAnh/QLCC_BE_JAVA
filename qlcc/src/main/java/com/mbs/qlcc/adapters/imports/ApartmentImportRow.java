package com.mbs.qlcc.adapters.imports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentImportRow {
    private String buildingId;      // Tòa nhà
    private int floor;              // Tầng
    private String aptNumber;       // Số căn hộ
    private Double grossArea;       // Diện tích sàn
    private Double coefficient;     // Hệ số
    private String aptType;         // Loại căn hộ
    private String description;     // Mô tả
    private List<String> errors;    // Validation errors
}
