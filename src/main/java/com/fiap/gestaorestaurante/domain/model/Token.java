package com.fiap.gestaorestaurante.domain.model;

public record Token(
        String token,
        String email
) {}