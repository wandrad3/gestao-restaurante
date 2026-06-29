package com.fiap.gestaorestaurante.infrastructure.web.dto;

import com.fiap.gestaorestaurante.core.domain.Restaurant;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        String cuisineType,
        String openingHours,
        Long ownerId,
        String ownerName
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(),
                restaurant.getOwner().getId(), restaurant.getOwner().getName()
        );
    }
}
