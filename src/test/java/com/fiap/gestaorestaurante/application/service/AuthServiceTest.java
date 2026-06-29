package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.domain.model.UserType;
import com.fiap.gestaorestaurante.infrastructure.persistence.UserRepository;
import com.fiap.gestaorestaurante.core.exception.CredenciaisInvalidasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService service;

    private User user;

    @BeforeEach
    void setUp() {
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
    void shouldLoadUserByEmail() {
        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(user));

        assertThat(service.loadUserByUsername("maria@email.com")).isSameAs(user);
    }

    @Test
    void shouldThrowWhenUserIsNotFoundByEmail() {
        when(userRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername("naoexiste@email.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Usuario nao encontrado");
    }

    @Test
    void shouldAuthenticateWhenPasswordMatches() {
        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha", "$2a$10$hash")).thenReturn(true);

        assertThat(service.autenticar("maria@email.com", "senha")).isSameAs(user);
        verify(passwordEncoder).matches("senha", "$2a$10$hash");
    }

    @Test
    void shouldRejectInvalidPassword() {
        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("errada", "$2a$10$hash")).thenReturn(false);

        assertThatThrownBy(() -> service.autenticar("maria@email.com", "errada"))
                .isInstanceOf(CredenciaisInvalidasException.class)
                .hasMessage("Email ou senha invalidos");
    }

    @Test
    void shouldRejectUnknownEmail() {
        when(userRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.autenticar("naoexiste@email.com", "senha"))
                .isInstanceOf(CredenciaisInvalidasException.class)
                .hasMessage("Email ou senha invalidos");
    }
}
