package com.mbs.qlcc.adapters.db.Token;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mbs.qlcc.adapters.db.User.UserDataMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "token")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "token", nullable = false,length = 1000)
    String token;

    @Column()
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    Date expirationDate;

    @Column(name = "refresh_token", nullable = false, length = 1000)
    String refreshToken;

    @Column(name = "refresh_expiration_token")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    Date refreshExpirationDate;

    @Column(name = "revoked")
    boolean revoked;

    @Column(name = "expried")
    boolean expired;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    UserDataMapper user;
}
