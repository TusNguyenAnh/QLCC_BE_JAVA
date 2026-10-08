package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "org_user")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgUser extends BaseEntity {
    @Id // Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @Column(name = "role_id", nullable = false)
    private String roleId;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
