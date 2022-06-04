package com.example.finalprojectaozcann.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.config.Properties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class JWTHelper {

    private final Properties properties;

    public String generateToken(Long identityNumber, UserDetail userDetail) {

        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(Date.from(Instant.now()).getTime() + properties.getExpiresIn()))
                .withClaim(Constants.Security.IDENTITY_NUMBER, identityNumber)
                .withClaim(Constants.Security.USER_ID, userDetail.getId())
                .sign(Algorithm.HMAC512(properties.getSecretKey()));
    }

}
