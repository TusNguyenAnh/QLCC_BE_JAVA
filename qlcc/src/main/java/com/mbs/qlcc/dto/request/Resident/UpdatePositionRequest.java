package com.mbs.qlcc.dto.request.Resident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePositionRequest {
    private String userId;
    private String orgId;
    private String roleId;
}
