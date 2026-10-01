package com.mbs.qlcc.repository.Authentication;

import com.mbs.qlcc.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<Token, String> {
    Token findByRefreshToken(String refreshToken);
    Token findByToken(String token);
}
