package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Permission;
import com.mbs.qlcc.domain.Role;
import com.mbs.qlcc.domain.RolePermission;
import com.mbs.qlcc.dto.request.Permission.AssignPermissionRequest;
import com.mbs.qlcc.dto.request.Permission.CreatePermissionRequest;
import com.mbs.qlcc.dto.response.Permission.PermissionResponse;
import com.mbs.qlcc.mapper.Authentication.PermissionMapper;
import com.mbs.qlcc.repository.Authentication.IPermissionRepository;
import com.mbs.qlcc.repository.Authentication.IRolePermissionRepository;
import com.mbs.qlcc.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements IPermissionService {

    IPermissionRepository permissionRepository;
    IRolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional
    public PermissionResponse create(CreatePermissionRequest request) {
        Permission permission = Permission.builder()
                .name(request.getName())
                .module(request.getModule())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Permission saved = permissionRepository.save(permission);
        return PermissionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('view:permission')")
    public Map<String, List<PermissionResponse>> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::toResponse)
                .collect(Collectors.groupingBy(PermissionResponse::getModule));
    }

    @Override
    @Transactional
    public String assignPermission(AssignPermissionRequest request) {
        // Delete all existing role permissions for this role
        rolePermissionRepository.deleteByRoleId(request.getRoleId());

        // Create new role-permission associations
        List<RolePermission> rolePermissions = request.getPermission().stream()
                .map(permissionId -> RolePermission.builder()
                        .permission(Permission.builder().id(permissionId).build())
                        .role(Role.builder().id(request.getRoleId()).build())
                        .build())
                .toList();

        rolePermissionRepository.saveAll(rolePermissions);
        return "Gán quyền thành công";
    }
}
