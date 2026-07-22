package com.mbs.qlcc.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtil {
    public static String SIGNER_KEY;

    public JwtUtil(@Value("${jwt.signerKey}")
                   String signerKey) {
        SIGNER_KEY = signerKey;
    }

    public static String getToken() {
        var authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getTokenValue();
    }

    public static JWTClaimsSet parseAndValidate(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            // verify signature
            MACVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            boolean isValid = signedJWT.verify(verifier);

            if (!isValid) {
                throw new RuntimeException("Invalid signature");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // check expiration
            Date expirationTime = claims.getExpirationTime();
            if (expirationTime != null && expirationTime.before(new Date())) {
                throw new RuntimeException("Token expired");
            }

            return claims;

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    // Lấy claim
    public static Map<String, Object> getClaim(String token) {
        try {
            return parseAndValidate(token).getClaims();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get claim: ", e);
        }
    }

    public static String getUserId(String token) {
        try {
            return parseAndValidate(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user id: ", e);
        }
    }

}
