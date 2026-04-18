package com.mbs.qlcc.adapters.db.Token;

import com.mbs.qlcc.adapters.db.Complex.ComplexDataMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTokenRepository extends JpaRepository<TokenDataMapper, String> {
    TokenDataMapper findByRefreshToken(String refreshToken);
    TokenDataMapper findByToken(String token);
}
