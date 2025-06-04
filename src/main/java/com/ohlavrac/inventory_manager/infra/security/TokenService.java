package com.ohlavrac.inventory_manager.infra.security;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ohlavrac.inventory_manager.domain.entities.user.UserDetailsImpl;

@Service
public class TokenService {
    private static final String SECRET_KEY = "thisisnotasecretkeyisjustatempkeyfordevelopmentcopianaocomedia";
    private static final String ISSUER = "ohlavrac-api";

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationAt())
                    .withExpiresAt(experationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error To Generate TOKEN", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid TOKEN");
        }
    }

    public Instant creationAt() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    public Instant experationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(2).toInstant();
    }
}
