package com.mbs.qlcc.dto.request.Resident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportAptResidentRequest {
    private String buildingName;
    private String apartmentNumber;
    private String cccd;
}
