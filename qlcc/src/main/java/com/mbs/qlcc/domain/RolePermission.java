package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permiss")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
