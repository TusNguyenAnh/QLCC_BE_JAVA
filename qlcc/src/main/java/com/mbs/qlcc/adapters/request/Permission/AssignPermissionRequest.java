package com.mbs.qlcc.adapters.request.Permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignPermissionRequest {
    private String roleId;
    private List<String> permission;
}
