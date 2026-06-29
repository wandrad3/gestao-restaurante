package com.fiap.gestaorestaurante.core.dto;

public record UserUpdateInputDto(
        String name,
        String email,
        String username,
        String street,
        String number,
        String city,
        String state,
        String zipCode,
        Long userTypeId
) {
}
