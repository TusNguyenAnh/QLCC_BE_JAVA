package com.mbs.qlcc.adapters.request.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleRequest {
    private String userId;
    private String roleId;
    private String orgId;
}
