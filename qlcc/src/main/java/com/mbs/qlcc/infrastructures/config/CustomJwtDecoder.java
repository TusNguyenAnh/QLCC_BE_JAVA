package com.mbs.qlcc.infrastructures.config;

import com.mbs.qlcc.adapters.services.AuthenticationService;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.request.Authentication.IntrospectTokenInpRequest;
import com.mbs.qlcc.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    private final AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    public CustomJwtDecoder(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        var response = authenticationService.introspect(new IntrospectTokenInpRequest(token));

        if (!response.isResValid()) throw new AppException(ErrorCode.UNAUTHENTICATED);

        //giai ma token tra ve doi tuong JWT
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
