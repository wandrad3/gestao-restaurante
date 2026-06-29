package com.fiap.gestaorestaurante.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.gestaorestaurante.core.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
    private final String secret;
    private final long expirationMillis;

    public TokenService(@Value("${jwt.secret:default-secret-change-this}") String secret,
                        @Value("${jwt.expiration:3600000}") long expirationMillis) {
        this.secret = secret == null ? "default-secret-change-this" : secret;
        this.expirationMillis = expirationMillis;
    }

    public String createToken(User user) {
        Instant now = Instant.now();
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        var builder = JWT.create()
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusMillis(expirationMillis)));

        if (user.getId() != null) {
            builder.withSubject(String.valueOf(user.getId()));
            builder.withClaim("id", user.getId());
        }
        builder.withClaim("email", user.getEmail());
        if (user.getUserType() != null) {
            builder.withClaim("userType", user.getUserType().getName());
            builder.withArrayClaim("roles", new String[]{user.getUserType().getName()});
        }
        return builder.sign(algorithm);
    }

    public User getUsuarioFromToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        String raw = token.trim();
        if (raw.startsWith("Bearer ")) {
            raw = raw.substring(7).trim();
        }
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .verify(raw);
            Long id = jwt.getClaim("id").isNull() ? null : jwt.getClaim("id").asLong();
            if (id == null && jwt.getSubject() != null && !jwt.getSubject().isBlank()) {
                id = Long.valueOf(jwt.getSubject());
            }
            return new User(
                    id, null, jwt.getClaim("email").asString(), null, null,
                    null, null, null, null, null, null, null
            );
        } catch (Exception ignored) {
            return null;
        }
    }
}
