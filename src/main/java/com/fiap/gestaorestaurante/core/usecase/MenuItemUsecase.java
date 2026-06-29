package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.MenuItem;
import com.fiap.gestaorestaurante.core.domain.Restaurant;
import com.fiap.gestaorestaurante.core.dto.MenuItemInputDto;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import com.fiap.gestaorestaurante.core.gateway.MenuItemGateway;

import java.util.List;

public class MenuItemUsecase {
    private final MenuItemGateway menuItemGateway;
    private final RestaurantUsecase restaurantUsecase;

    public MenuItemUsecase(MenuItemGateway menuItemGateway, RestaurantUsecase restaurantUsecase) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantUsecase = restaurantUsecase;
    }

    public MenuItem create(MenuItemInputDto input) {
        Restaurant restaurant = restaurantUsecase.findById(input.restaurantId());
        return menuItemGateway.save(new MenuItem(
                restaurant, input.name().trim(), input.description().trim(), input.price(),
                input.dineInOnly(), input.photoPath().trim()
        ));
    }

    public List<MenuItem> findAll(Long restaurantId) {
        if (restaurantId == null) {
            return menuItemGateway.findAll();
        }
        restaurantUsecase.findById(restaurantId);
        return menuItemGateway.findAllByRestaurantIdOrderByName(restaurantId);
    }

    public MenuItem findById(Long id) {
        return menuItemGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio não encontrado: " + id));
    }

    public MenuItem update(Long id, MenuItemInputDto input) {
        MenuItem item = findById(id);
        Restaurant restaurant = restaurantUsecase.findById(input.restaurantId());
        item.update(
                restaurant, input.name().trim(), input.description().trim(), input.price(),
                input.dineInOnly(), input.photoPath().trim()
        );
        return menuItemGateway.save(item);
    }

    public void delete(Long id) {
        menuItemGateway.delete(findById(id));
    }
}
