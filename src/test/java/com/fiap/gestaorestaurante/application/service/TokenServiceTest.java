package com.fiap.gestaorestaurante.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.domain.UserType;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class TokenServiceTest {

    private final TokenService service = new TokenService("test-secret", 3600000);

    @Test
    void shouldCreateTokenWithUserClaimsAndDecodeUser() {
        User user = new User(
                "Maria Silva",
                "maria@email.com",
                "maria",
                "senha",
                "Rua A",
                "10",
                "Sao Paulo",
                "SP",
                "01000-000",
                new UserType("ADMIN")
        );

        String token = service.createToken(user);

        assertThat(token).isNotBlank();
        var decoded = JWT.require(Algorithm.HMAC256("test-secret".getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token);
        assertThat(decoded.getClaim("email").asString()).isEqualTo("maria@email.com");
        assertThat(decoded.getClaim("roles").asArray(String.class)).contains("ADMIN");
        assertThat(decoded.getClaim("userType").asString()).isEqualTo("ADMIN");

        User decodedUser = service.getUsuarioFromToken(token);

        assertThat(decodedUser).isNotNull();
        assertThat(decodedUser.getEmail()).isEqualTo("maria@email.com");
    }

    @Test
    void shouldAcceptTokenWithBearerPrefix() {
        User user = new User(
                "Maria Silva",
                "maria@email.com",
                "maria",
                "senha",
                "Rua A",
                "10",
                "Sao Paulo",
                "SP",
                "01000-000",
                new UserType("ADMIN")
        );

        User decodedUser = service.getUsuarioFromToken("Bearer " + service.createToken(user));

        assertThat(decodedUser).isNotNull();
        assertThat(decodedUser.getEmail()).isEqualTo("maria@email.com");
    }

    @Test
    void shouldReturnNullForBlankOrInvalidToken() {
        assertThat(service.getUsuarioFromToken(null)).isNull();
        assertThat(service.getUsuarioFromToken("   ")).isNull();
        assertThat(service.getUsuarioFromToken("token-invalido")).isNull();
    }
}
