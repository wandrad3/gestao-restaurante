package com.fiap.gestaorestaurante.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Email @Size(max = 180) String email,
        @NotBlank @Size(max = 80) String username,
        @NotBlank @Size(min = 8, max = 100) String password,
        @NotBlank @Size(max = 120) String street,
        @NotBlank @Size(max = 20) String number,
        @NotBlank @Size(max = 80) String city,
        @NotBlank @Pattern(regexp = "[A-Za-z]{2}") String state,
        @NotBlank @Pattern(regexp = "\\d{5}-?\\d{3}") String zipCode,
        @NotNull Long userTypeId
) {
}
