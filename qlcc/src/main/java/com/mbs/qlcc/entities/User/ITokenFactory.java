package com.mbs.qlcc.entities.User;

import java.util.Date;

public interface ITokenFactory {
    Token create(String token, Date expirationDate, String refreshToken, Date refreshExpirationDate,
                 boolean revoked, boolean expired);
}
