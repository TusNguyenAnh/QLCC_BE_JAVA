package com.mbs.qlcc.adapters.db.Authentication;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import com.mbs.qlcc.entities.Authentication.Role;
import com.mbs.qlcc.usecases.output.Role.IRoleDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Permission.ICountRoleResponse;
import com.mbs.qlcc.usecases.response.Role.ICountRolePermissionResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleResponse;
import com.mbs.qlcc.usecases.response.Role.IRoleUserResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRole implements IRoleDsGateway {
    private final JpaRoleRepository jpaRoleRepository;

    @Override
    public Role createRole(Role role) {
        RoleDataMapper mapper = mapToRoleDataMapper(role);
        mapper.setStatus(false);
        mapper.setCreatedAt(LocalDateTime.now());
        mapper.setUpdatedAt(LocalDateTime.now());
        RoleDataMapper saved = jpaRoleRepository.save(mapper);
        return mapToRole(saved);
    }

    @Override
    public PageResponse<IRoleResponse> findByComplexId(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<IRoleResponse> roleDataMappers = jpaRoleRepository.findByComplexIdAndStatus(complexId, false, pageable);
        return new PageResponse<>(
                roleDataMappers.getContent(),
                roleDataMappers.getNumber(),
                roleDataMappers.getSize(),
                roleDataMappers.getTotalElements(),
                roleDataMappers.getTotalPages()
        );
    }

    @Override
    public Role findByRoleName(String roleName, String complexId) {
        return mapToRole(jpaRoleRepository.findByRoleNameAndComplexId(roleName, complexId));
    }

    @Override
    public Role findById(String roleId) {
        return jpaRoleRepository.findById(roleId)
                .map(this::mapToRole)
                .orElse(null);
    }

    @Override
    public Map<String, Integer> countUserById(String complexId) {
        return jpaRoleRepository.countUserById(complexId).stream()
                .collect(Collectors.toMap(IRoleUserResponse::getRoleId, IRoleUserResponse::getUserCount));
    }

    @Override
    public Map<String, Integer> countRoleById(String complexId) {
        return jpaRoleRepository.countRoleById(complexId).stream()
                .collect(Collectors.toMap(ICountRoleResponse::getPermissionId, ICountRoleResponse::getRoleCount));
    }

    private Role mapToRole(RoleDataMapper roleDataMapper) {
        if (roleDataMapper == null) {
            return null;
        }
        return new Role(roleDataMapper.getId(), roleDataMapper.getRoleName(), roleDataMapper.getComplexId(), roleDataMapper.getDescription());
    }

    private RoleDataMapper mapToRoleDataMapper(Role role) {
        if (role == null) {
            return null;
        }
        return RoleDataMapper.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .complexId(role.getComplexId())
                .description(role.getDescription())
                .build();
    }
}
