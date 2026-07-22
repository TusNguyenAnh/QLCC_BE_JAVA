package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.Role.AssignRoleRequest;
import com.mbs.qlcc.adapters.request.Role.CreateRoleRequest;
import com.mbs.qlcc.usecases.input.IRoleInputBoundary;
import com.mbs.qlcc.usecases.request.Role.AssignRoleInpRequest;
import com.mbs.qlcc.usecases.request.Role.CreateRoleInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleResponse;
import com.mbs.qlcc.usecases.response.Role.RoleResponse;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    IRoleInputBoundary useCase;

    public RoleResponse createRole(CreateRoleRequest roleRequest){
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();

        return useCase.createRole(new CreateRoleInpRequest(
                roleRequest.getRoleName(),
                roleRequest.getDescription(),
                complexId
        ));
    }

    public String assignRole(AssignRoleRequest assignRoleRequest){
        AssignRoleInpRequest assignRoleInpRequest = new AssignRoleInpRequest(
                assignRoleRequest.getUserId(),
                assignRoleRequest.getRoleId(),
                assignRoleRequest.getOrgId()
        );
        return useCase.assignRole(assignRoleInpRequest);
    }

    public PageResponse<IRoleResponse> getAllRoles(int page, int size){
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        return useCase.getAllRoles(complexId, page, size);
    }
    public String getRoleByUserId(String userId,String orgId){
        return useCase.getRoleByUserId(userId,orgId);
    }

    public Map<String, Integer> getRoleUserCount(){
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        return useCase.getRoleUserCount(complexId);
    }
    public Map<String, Integer> getRolePermissionCount(){
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        return useCase.getRolePermissionCount(complexId);
    }

}
