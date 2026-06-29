package com.fiap.gestaorestaurante.core.dto;

public record UserInputDto(
        String name,
        String email,
        String username,
        String password,
        String street,
        String number,
        String city,
        String state,
        String zipCode,
        Long userTypeId
) {
}
