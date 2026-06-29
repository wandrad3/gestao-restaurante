package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.infrastructure.persistence.UserRepository;
import com.fiap.gestaorestaurante.core.exception.CredenciaisInvalidasException;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
    }

    public User autenticar(String email, String senha) {
        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("Email ou senha invalidos"));

        if (!passwordEncoder.matches(senha, usuario.getPassword())) {
            throw new CredenciaisInvalidasException("Email ou senha invalidos");
        }

        return usuario;
    }
}
