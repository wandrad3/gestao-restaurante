package com.fiap.gestaorestaurante.core.gateway;

public interface PasswordGateway {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
