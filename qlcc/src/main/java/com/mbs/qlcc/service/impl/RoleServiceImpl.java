package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.Role;
import com.mbs.qlcc.dto.request.Role.AssignRoleRequest;
import com.mbs.qlcc.dto.request.Role.CreateRoleRequest;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Permission.ICountRoleResponse;
import com.mbs.qlcc.dto.response.Role.IRoleResponse;
import com.mbs.qlcc.dto.response.Role.IRoleUserResponse;
import com.mbs.qlcc.dto.response.Role.RoleResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Authentication.RoleMapper;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.Role.IRoleRepository;
import com.mbs.qlcc.service.IRoleService;
import com.mbs.qlcc.utils.ErrorCode;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements IRoleService {

    IRoleRepository roleRepository;
    IOrgUserRepository orgUserRepository;

    @Override
    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();

        Role role = Role.builder()
                .roleName(request.getRoleName())
                .complexId(complexId)
                .description(request.getDescription())
                .status(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Role saved = roleRepository.save(role);
        return RoleMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<IRoleResponse> getAllRoles(int page, int size) {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<IRoleResponse> result = roleRepository.findByComplexIdAndStatus(complexId, false, pageable);
        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    @Transactional
    public String assignRole(AssignRoleRequest request) {
        OrgUser orgUser = orgUserRepository.findByUserIdAndOrgId(request.getUserId(), request.getOrgId());
        if (orgUser == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        orgUser.setRoleId(request.getRoleId());
        orgUser.setUpdatedAt(LocalDateTime.now());
        orgUserRepository.save(orgUser);
        return "Role assigned successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public String getRoleByUserId(String userId, String orgId) {
        OrgUser orgUser = orgUserRepository.findByUserIdAndOrgId(userId, orgId);
        if (orgUser == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return orgUser.getRoleId();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Integer> getRoleUserCount() {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        List<IRoleUserResponse> results = roleRepository.countUserById(complexId);
        return results.stream()
                .collect(Collectors.toMap(IRoleUserResponse::getRoleId, IRoleUserResponse::getUserCount));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Integer> getRolePermissionCount() {
        String complexId = JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
        List<ICountRoleResponse> results = roleRepository.countRoleById(complexId);
        return results.stream()
                .collect(Collectors.toMap(ICountRoleResponse::getPermissionId, ICountRoleResponse::getRoleCount));
    }
}
