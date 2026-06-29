package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.Restaurant;
import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.dto.RestaurantInputDto;
import com.fiap.gestaorestaurante.core.exception.ConflictException;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import com.fiap.gestaorestaurante.core.gateway.MenuItemGateway;
import com.fiap.gestaorestaurante.core.gateway.RestaurantGateway;

import java.util.List;

public class RestaurantUsecase {
    private final RestaurantGateway restaurantGateway;
    private final MenuItemGateway menuItemGateway;
    private final UserUsecase userUsecase;

    public RestaurantUsecase(RestaurantGateway restaurantGateway, MenuItemGateway menuItemGateway,
                             UserUsecase userUsecase) {
        this.restaurantGateway = restaurantGateway;
        this.menuItemGateway = menuItemGateway;
        this.userUsecase = userUsecase;
    }

    public Restaurant create(RestaurantInputDto input) {
        User owner = userUsecase.findById(input.ownerId());
        return restaurantGateway.save(new Restaurant(
                input.name().trim(), input.address().trim(), input.cuisineType().trim(),
                input.openingHours().trim(), owner
        ));
    }

    public List<Restaurant> findAll() {
        return restaurantGateway.findAll();
    }

    public Restaurant findById(Long id) {
        return restaurantGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));
    }

    public Restaurant update(Long id, RestaurantInputDto input) {
        Restaurant restaurant = findById(id);
        User owner = userUsecase.findById(input.ownerId());
        restaurant.update(
                input.name().trim(), input.address().trim(), input.cuisineType().trim(),
                input.openingHours().trim(), owner
        );
        return restaurantGateway.save(restaurant);
    }

    public void delete(Long id) {
        if (menuItemGateway.existsByRestaurantId(id)) {
            throw new ConflictException("Restaurante possui itens de cardápio e não pode ser removido");
        }
        restaurantGateway.delete(findById(id));
    }
}
