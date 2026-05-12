package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Authentication.IRoleFactory;
import com.mbs.qlcc.entities.Authentication.Permission;
import com.mbs.qlcc.entities.Authentication.Role;
import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IRoleInputBoundary;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.Role.IRoleDsGateway;
import com.mbs.qlcc.usecases.request.Role.AssignRoleInpRequest;
import com.mbs.qlcc.usecases.request.Role.CreateRoleInpRequest;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Permission.PermissionResponse;
import com.mbs.qlcc.usecases.response.Role.RoleResponse;
import com.mbs.qlcc.utils.ErrorCode;

public class RoleInteractor implements IRoleInputBoundary {
    private IRoleDsGateway roleDsGateway;
    private IOrgUserDsGateway orgUserDsGateway;
    private IRoleFactory roleFactory;


    public RoleInteractor(IRoleDsGateway roleDsGateway, IOrgUserDsGateway orgUserDsGateway, IRoleFactory roleFactory) {
        this.roleDsGateway = roleDsGateway;
        this.orgUserDsGateway = orgUserDsGateway;
        this.roleFactory = roleFactory;
    }

    @Override
    public RoleResponse createRole(CreateRoleInpRequest roleRequest) {
        Role role = roleFactory.createRole(roleRequest.getRoleName(), roleRequest.getComplexId(), roleRequest.getDescription());
        return mapToResponse(roleDsGateway.createRole(role));
    }

    @Override
    public PageResponse<RoleResponse> getAllRoles(String complexId, int page, int size) {
        PageResponse<Role> result = roleDsGateway.findByComplexId(complexId, page, size);
        return new PageResponse<RoleResponse>(
                result.getData().stream().map(this::mapToResponse).toList(),
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public String assignRole(AssignRoleInpRequest assignRoleInpRequest) {
        OrgUser orgUser = orgUserDsGateway.findUserByOrgId(assignRoleInpRequest.getUserId(), assignRoleInpRequest.getOrgId());
        if (orgUser == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        orgUserDsGateway.updateOrgUser(new OrgUser(assignRoleInpRequest.getUserId(), assignRoleInpRequest.getOrgId(), assignRoleInpRequest.getRoleId()));
        return "Role assigned successfully";
    }

    @Override
    public String getRoleByUserId(String userId, String orgId) {
        OrgUser orgUser = orgUserDsGateway.findUserByOrgId(userId, orgId);
        if (orgUser == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return orgUser.getRole_id();
    }

    private RoleResponse mapToResponse(Role role) {
        return new RoleResponse(role.getId(), role.getRoleName(), role.getComplexId(), role.getDescription());
    }
}
