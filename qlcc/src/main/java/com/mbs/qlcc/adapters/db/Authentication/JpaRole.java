package com.mbs.qlcc.adapters.db.Authentication;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import com.mbs.qlcc.entities.Authentication.Role;
import com.mbs.qlcc.usecases.output.Role.IRoleDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public PageResponse<Role> findByComplexId(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<RoleDataMapper> roleDataMappers = jpaRoleRepository.findByComplexIdAndStatus(complexId, false, pageable);

        return new PageResponse<>(
                roleDataMappers.getContent().stream().map(this::mapToRole).collect(Collectors.toList()),
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
