package com.fiap.gestaorestaurante.infrastructure.web.dto;

import com.fiap.gestaorestaurante.core.domain.MenuItem;

import java.math.BigDecimal;

public record MenuItemResponse(
        Long id,
        Long restaurantId,
        String restaurantName,
        String name,
        String description,
        BigDecimal price,
        boolean dineInOnly,
        String photoPath
) {
    public static MenuItemResponse from(MenuItem item) {
        return new MenuItemResponse(
                item.getId(), item.getRestaurant().getId(), item.getRestaurant().getName(),
                item.getName(), item.getDescription(), item.getPrice(),
                item.isDineInOnly(), item.getPhotoPath()
        );
    }
}
