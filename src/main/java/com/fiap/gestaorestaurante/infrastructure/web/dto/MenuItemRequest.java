package com.fiap.gestaorestaurante.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MenuItemRequest(
        @NotNull Long restaurantId,
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 500) String description,
        @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal price,
        boolean dineInOnly,
        @NotBlank @Size(max = 500) String photoPath
) {
}
