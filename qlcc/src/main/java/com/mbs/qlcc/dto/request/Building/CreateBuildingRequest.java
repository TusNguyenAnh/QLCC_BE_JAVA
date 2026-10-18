package com.mbs.qlcc.dto.request.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBuildingRequest {
    private String complexId;
    private String buildingName;
    private Float financialRatio;
    private List<MultipartFile> files;
}
