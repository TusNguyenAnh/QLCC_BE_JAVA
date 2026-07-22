package com.mbs.qlcc.adapters.db.User;

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
public class UserDataMapper {
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

    @Column(name = "created_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
