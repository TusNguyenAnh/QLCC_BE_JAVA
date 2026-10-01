package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.Role.AssignRoleRequest;
import com.mbs.qlcc.dto.request.Role.CreateRoleRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Role.IRoleResponse;
import com.mbs.qlcc.dto.response.Role.RoleResponse;

import java.util.Map;

public interface IRoleService {
    RoleResponse createRole(CreateRoleRequest request);
    PageResponse<IRoleResponse> getAllRoles(int page, int size);
    String assignRole(AssignRoleRequest request);
    String getRoleByUserId(String userId, String orgId);
    Map<String, Integer> getRoleUserCount();
    Map<String, Integer> getRolePermissionCount();
}
