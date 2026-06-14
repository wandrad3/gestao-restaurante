package com.fiap.gestaorestaurante.infrastructure.web.dto;

import com.fiap.gestaorestaurante.domain.model.UserType;

public record UserTypeResponse(Long id, String name) {
    public static UserTypeResponse from(UserType type) {
        return new UserTypeResponse(type.getId(), type.getName());
    }
}
