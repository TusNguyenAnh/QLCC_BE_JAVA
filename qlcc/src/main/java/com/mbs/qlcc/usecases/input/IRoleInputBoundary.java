package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.entities.Authentication.Role;
import com.mbs.qlcc.usecases.request.Role.AssignRoleInpRequest;
import com.mbs.qlcc.usecases.request.Role.CreateRoleInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleResponse;
import com.mbs.qlcc.usecases.response.Role.RoleResponse;

import java.util.Map;

public interface IRoleInputBoundary {
    RoleResponse createRole(CreateRoleInpRequest roleRequest);

    String assignRole(AssignRoleInpRequest assignRoleInpRequest);

    PageResponse<IRoleResponse> getAllRoles(String complexId, int page, int size);
    String getRoleByUserId(String userId,String orgId);
    Map<String, Integer> getRoleUserCount(String complexId);
    Map<String, Integer> getRolePermissionCount(String complexId);

}
