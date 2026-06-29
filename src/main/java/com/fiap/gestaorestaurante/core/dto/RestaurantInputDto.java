package com.fiap.gestaorestaurante.core.dto;

public record RestaurantInputDto(
        String name,
        String address,
        String cuisineType,
        String openingHours,
        Long ownerId
) {
}
