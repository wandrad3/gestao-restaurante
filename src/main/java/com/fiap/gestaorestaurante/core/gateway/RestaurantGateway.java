package com.fiap.gestaorestaurante.core.gateway;

import com.fiap.gestaorestaurante.core.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    Restaurant save(Restaurant restaurant);
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    boolean existsByOwnerId(Long ownerId);
    void delete(Restaurant restaurant);
}
