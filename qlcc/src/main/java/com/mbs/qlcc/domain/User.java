package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "username", nullable = false, length = 100)
    String username;

    @Column(name = "password_hash", nullable = false, length = 250)
    String passwordHash;

    @Column(name = "complex_id", length = 100)
    String complexId;

    @Column(name = "res_id", length = 100)
    String resId;

    @Column(name = "staff_id", length = 100)
    String staffId;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
