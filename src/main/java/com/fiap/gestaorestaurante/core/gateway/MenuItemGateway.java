package com.fiap.gestaorestaurante.core.gateway;

import com.fiap.gestaorestaurante.core.domain.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemGateway {
    MenuItem save(MenuItem item);
    List<MenuItem> findAll();
    List<MenuItem> findAllByRestaurantIdOrderByName(Long restaurantId);
    Optional<MenuItem> findById(Long id);
    boolean existsByRestaurantId(Long restaurantId);
    void delete(MenuItem item);
}
