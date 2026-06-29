package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.exception.CredenciaisInvalidasException;
import com.fiap.gestaorestaurante.core.gateway.PasswordGateway;
import com.fiap.gestaorestaurante.core.gateway.UserGateway;

public class AuthUsecase {
    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public AuthUsecase(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public User autenticar(String email, String senha) {
        User usuario = userGateway.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("Email ou senha invalidos"));

        if (!passwordGateway.matches(senha, usuario.getPassword())) {
            throw new CredenciaisInvalidasException("Email ou senha invalidos");
        }

        return usuario;
    }
}
