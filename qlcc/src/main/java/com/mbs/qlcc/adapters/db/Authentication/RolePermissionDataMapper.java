package com.mbs.qlcc.adapters.db.Authentication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permiss")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePermissionDataMapper {
    @Id // Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private PermissionDataMapper permissionDataMapper;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleDataMapper roleDataMapper;
}
