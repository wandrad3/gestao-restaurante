package com.fiap.gestaorestaurante.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RestaurantRequest(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 255) String address,
        @NotBlank @Size(max = 100) String cuisineType,
        @NotBlank @Size(max = 120) String openingHours,
        @NotNull Long ownerId
) {
}
