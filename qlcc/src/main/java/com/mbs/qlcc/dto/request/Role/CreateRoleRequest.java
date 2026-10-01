package com.mbs.qlcc.dto.request.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoleRequest {
    private String roleName;
    private String description;
}
