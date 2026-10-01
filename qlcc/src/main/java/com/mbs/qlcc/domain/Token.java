package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "token", nullable = false, length = 1000)
    String token;

    @Column(name = "expiration_date")
    Date expirationDate;

    @Column(name = "refresh_token", nullable = false, length = 1000)
    String refreshToken;

    @Column(name = "refresh_expiration_token")
    Date refreshExpirationDate;

    @Column(name = "revoked")
    boolean revoked;

    @Column(name = "expried")
    boolean expired;

    @Column(name = "user_id", nullable = false)
    String userId;
}
