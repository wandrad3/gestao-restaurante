package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.domain.UserType;
import com.fiap.gestaorestaurante.core.exception.CredenciaisInvalidasException;
import com.fiap.gestaorestaurante.core.gateway.PasswordGateway;
import com.fiap.gestaorestaurante.core.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUsecaseTest {
    @Mock
    private UserGateway userGateway;
    @Mock
    private PasswordGateway passwordGateway;

    private AuthUsecase usecase;
    private User user;

    @BeforeEach
    void setUp() {
        usecase = new AuthUsecase(userGateway, passwordGateway);
        user = new User(
                "Maria Silva",
                "maria@email.com",
                "maria",
                "$2a$10$hash",
                "Rua A",
                "10",
                "Sao Paulo",
                "SP",
                "01000-000",
                new UserType("ADMIN")
        );
    }

    @Test
    void shouldAuthenticateWhenPasswordMatches() {
        when(userGateway.findByEmail("maria@email.com")).thenReturn(Optional.of(user));
        when(passwordGateway.matches("senha", "$2a$10$hash")).thenReturn(true);

        assertThat(usecase.autenticar("maria@email.com", "senha")).isSameAs(user);
        verify(passwordGateway).matches("senha", "$2a$10$hash");
    }

    @Test
    void shouldRejectInvalidPassword() {
        when(userGateway.findByEmail("maria@email.com")).thenReturn(Optional.of(user));
        when(passwordGateway.matches("errada", "$2a$10$hash")).thenReturn(false);

        assertThatThrownBy(() -> usecase.autenticar("maria@email.com", "errada"))
                .isInstanceOf(CredenciaisInvalidasException.class)
                .hasMessage("Email ou senha invalidos");
    }

    @Test
    void shouldRejectUnknownEmail() {
        when(userGateway.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usecase.autenticar("naoexiste@email.com", "senha"))
                .isInstanceOf(CredenciaisInvalidasException.class)
                .hasMessage("Email ou senha invalidos");
    }
}
