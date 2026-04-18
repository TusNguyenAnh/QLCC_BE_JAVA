package com.mbs.qlcc.entities.User;

import java.util.Date;

public class Token {
    String token;
    Date expirationDate;
    String refreshToken;
    Date refreshExpirationDate;
    boolean revoked;
    boolean expired;

    public Token() {
    }

    public Token(String token, Date expirationDate, String refreshToken, Date refreshExpirationDate, boolean revoked, boolean expired) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.refreshToken = refreshToken;
        this.refreshExpirationDate = refreshExpirationDate;
        this.revoked = revoked;
        this.expired = expired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshExpirationDate() {
        return refreshExpirationDate;
    }

    public void setRefreshExpirationDate(Date refreshExpirationDate) {
        this.refreshExpirationDate = refreshExpirationDate;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
