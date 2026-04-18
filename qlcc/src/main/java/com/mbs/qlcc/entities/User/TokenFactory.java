package com.mbs.qlcc.entities.User;

import java.util.Date;

public class TokenFactory implements ITokenFactory {
    @Override
    public Token create(String token, Date expirationDate, String refreshToken, Date refreshExpirationDate, boolean revoked, boolean expired) {
        return new Token(token, expirationDate, refreshToken, refreshExpirationDate, revoked, expired);
    }
}
