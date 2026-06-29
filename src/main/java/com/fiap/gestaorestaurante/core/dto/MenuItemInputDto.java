package com.fiap.gestaorestaurante.core.dto;

import java.math.BigDecimal;

public record MenuItemInputDto(
        Long restaurantId,
        String name,
        String description,
        BigDecimal price,
        boolean dineInOnly,
        String photoPath
) {
}
