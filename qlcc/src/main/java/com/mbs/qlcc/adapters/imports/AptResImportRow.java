package com.mbs.qlcc.adapters.imports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptResImportRow {
    String buildingName;
    String aptName;
    String cccd;
    List<String> errors;
}
