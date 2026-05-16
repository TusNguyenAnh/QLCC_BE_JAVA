package com.mbs.qlcc.infrastructures.init;

import com.mbs.qlcc.adapters.db.Authentication.*;
import com.mbs.qlcc.adapters.db.FinancialModel.FinancialModelDataMapper;
import com.mbs.qlcc.adapters.db.FinancialModel.JpaFinancialModelRepository;
import com.mbs.qlcc.adapters.db.Organization.JpaOrgUserRepository;
import com.mbs.qlcc.adapters.db.Organization.OrgUserDataMapper;
import com.mbs.qlcc.adapters.db.User.JpaUserRepository;
import com.mbs.qlcc.adapters.db.User.UserDataMapper;
import com.mbs.qlcc.utils.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    final PasswordEncoder encoder;
    private final JpaRoleRepository roleRepository;
    private final JpaPermissionRepository permissionRepository;
    private final JpaUserRepository userRepository;
    private final JpaRolePermissionRepository rolePermissionRepository;
    private final JpaOrgUserRepository orgUserRepository;
    private final JpaFinancialModelRepository financialModelRepository;

    @Bean
    @Transactional
    CommandLineRunner init() {
        return args -> {
            if (roleRepository.count() > 0 || permissionRepository.count() > 0 || userRepository.count() > 0 ||
                    rolePermissionRepository.count() > 0 || orgUserRepository.count() > 0) {
                return;
            }

            //permission
            List<PermissionDataMapper> permissions = List.of(
                    // apartment
                    buildPermission("view:apartment", "apartment", "Xem danh sách căn hộ"),
                    buildPermission("manage:apartment", "apartment", "Thêm, sửa, xóa thông tin căn hộ"),

                    // building
                    buildPermission("view:building", "building", "Xem danh sách tòa nhà"),
                    buildPermission("manage:building", "building", "Thêm, sửa, xóa thông tin tòa nhà"),

                    // organization
                    buildPermission("view:organization", "organization", "Xem danh sách ban quản trị"),
                    buildPermission("manage:organization", "organization", "Thêm, sửa, xóa thông tin ban quản trị"),

                    // permission
                    buildPermission("view:permission", "permission", "Xem danh sách quyền hạn"),
                    buildPermission("assign:permission", "permission", "Gán quyền hạn cho vai trò"),

                    // priority
                    buildPermission("view:priority", "priority", "Lấy danh sách độ ưu tiên"),

                    // resident
                    buildPermission("view:resident", "resident", "Xem danh sách cư dân"),
                    buildPermission("manage:resident", "resident", "Thêm, sửa, xóa thông tin cư dân"),

                    // role
                    buildPermission("view:role", "role", "Xem danh sách vai trò"),
                    buildPermission("manage:role", "role", "Thêm, sửa, xóa thông tin vai trò"),
                    buildPermission("assign:role", "role", "Gán vai trò cho cư dân"),

                    // task
                    buildPermission("view:task", "task", "Xem danh sách yêu cầu"),
                    buildPermission("manage:task", "task", "Thêm, sửa, xóa thông tin yêu cầu"),
                    buildPermission("review:task", "task", "Xét duyệt yêu cầu"),

                    // taskType
                    buildPermission("view:taskType", "taskType", "Xem danh sách loại yêu cầu"),
                    buildPermission("manage:taskType", "taskType", "Thêm, sửa, xóa loại yêu cầu"),

                    // user
                    buildPermission("view:user", "user", "Xem danh sách người dùng"),
                    buildPermission("manage:user", "user", "Thêm, sửa, xóa người dùng"),

                    // workflow
                    buildPermission("view:workflow", "workflow", "Xem danh sách quy trình nghiệp vụ"),
                    buildPermission("manage:workflow", "workflow", "Thêm, sửa, xóa quy trình nghiệp vụ"),

                    // complex
                    buildPermission("view:complex", "complex", "Xem danh sách chung cư"),
                    buildPermission("manage:complex", "complex", "Thêm, sửa, xóa chung cư"),
                    buildPermission("review:complex", "complex", "Xét duyệt yêu cầu chung cư"),

                    // expense
                    buildPermission("view:expense", "expense", "Xem danh sách khoản chi"),
                    buildPermission("manage:expense", "expense", "Thêm, sửa, xóa khoản chi"),

                    // revenue
                    buildPermission("view:revenue", "revenue", "Xem danh sách khoản thu"),
                    buildPermission("manage:revenue", "revenue", "Thêm, sửa, xóa khoản thu")
            );

            // role
            List<RoleDataMapper> roles = List.of(
                    buildRole("suadmin"),
                    buildRole("admin")
            );

            //tao db seed permission va role, user
            permissionRepository.saveAll(permissions);
            roleRepository.saveAll(roles);
            userRepository.save(buildUser("atus"));

            // role-permission
            // gán quyền cho vai trò
            RoleDataMapper suadminRole = roleRepository.findByRoleNameAndComplexId("suadmin", "");
            RoleDataMapper adminRole = roleRepository.findByRoleNameAndComplexId("admin", "");

            List<String> suPermissions = permissionRepository.findByModule(List.of("complex", "user"), 2);
            List<String> adPermissions = permissionRepository.findByModule(List.of("complex", "task"), 1);

            List<RolePermissionDataMapper> suRolePermissions = suPermissions.stream()
                    .map(p -> buildRolePermiss(suadminRole.getId(), p))
                    .toList();

            List<RolePermissionDataMapper> adRolePermissions = adPermissions.stream()
                    .map(p -> buildRolePermiss(adminRole.getId(), p))
                    .toList();

            rolePermissionRepository.saveAll(suRolePermissions);
            rolePermissionRepository.saveAll(adRolePermissions);

            //org-user
            UserDataMapper admin = userRepository.findByUsernameAndComplexId("atus", "");
            RoleDataMapper suAdminRole = roleRepository.findByRoleNameAndComplexId("suadmin", "");
            orgUserRepository.save(buildOrgUser(admin.getId(), suAdminRole.getId()));

            // financial model
            List<FinancialModelDataMapper> financialModels = List.of(
                    buildFinancialModel("Mô hình tài chính tập trung", Constant.CENTRALIZED_FINANCIAL_MODEL.getValue()),
                    buildFinancialModel("Mô hình tài chính phân tán", Constant.DECENTRALIZED_FINANCIAL_MODEL.getValue())
            );
            financialModelRepository.saveAll(financialModels);
        };
    }

    private PermissionDataMapper buildPermission(String name, String module, String description) {
        return PermissionDataMapper.builder()
                .name(name)
                .module(module)
                .description(description)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private RoleDataMapper buildRole(String roleName) {
        return RoleDataMapper.builder()
                .roleName(roleName)
                .complexId("")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private UserDataMapper buildUser(String username) {
        return UserDataMapper.builder()
                .resId("")
                .complexId("")
                .username(username)
                .passwordHash(encoder.encode("123"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private RolePermissionDataMapper buildRolePermiss(String roleId, String permissionId) {
        return RolePermissionDataMapper.builder()
                .roleDataMapper(RoleDataMapper.builder().id(roleId).build())
                .permissionDataMapper(PermissionDataMapper.builder().id(permissionId).build())
                .build();
    }

    private OrgUserDataMapper buildOrgUser(String userId, String roleId) {
        return OrgUserDataMapper.builder()
                .userId(userId)
                .roleId(roleId)
                .orgId("")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private FinancialModelDataMapper buildFinancialModel(String name, String type) {
        return FinancialModelDataMapper.builder()
                .name(name)
                .type(type)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
