package com.fiap.gestaorestaurante.infrastructure.web.dto;

import com.fiap.gestaorestaurante.core.domain.User;

import java.time.OffsetDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        String username,
        String street,
        String number,
        String city,
        String state,
        String zipCode,
        UserTypeResponse userType,
        OffsetDateTime updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(), user.getUsername(),
                user.getStreet(), user.getNumber(), user.getCity(), user.getState(),
                user.getZipCode(), UserTypeResponse.from(user.getUserType()), user.getUpdatedAt()
        );
    }
}
