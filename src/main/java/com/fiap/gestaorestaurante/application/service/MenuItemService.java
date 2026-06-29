package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.MenuItem;
import com.fiap.gestaorestaurante.domain.model.Restaurant;
import com.fiap.gestaorestaurante.infrastructure.persistence.MenuItemRepository;
import com.fiap.gestaorestaurante.infrastructure.web.dto.MenuItemRequest;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class MenuItemService {

    private final MenuItemRepository repository;
    private final RestaurantService restaurantService;

    public MenuItemService(MenuItemRepository repository, RestaurantService restaurantService) {
        this.repository = repository;
        this.restaurantService = restaurantService;
    }

    public MenuItem create(MenuItemRequest request) {
        Restaurant restaurant = restaurantService.findById(request.restaurantId());
        return repository.save(new MenuItem(
                restaurant, request.name().trim(), request.description().trim(), request.price(),
                request.dineInOnly(), request.photoPath().trim()
        ));
    }

    @Transactional(readOnly = true)
    public List<MenuItem> findAll(Long restaurantId) {
        if (restaurantId == null) {
            return repository.findAll();
        }
        restaurantService.findById(restaurantId);
        return repository.findAllByRestaurantIdOrderByName(restaurantId);
    }

    @Transactional(readOnly = true)
    public MenuItem findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio não encontrado: " + id));
    }

    public MenuItem update(Long id, MenuItemRequest request) {
        MenuItem item = findById(id);
        Restaurant restaurant = restaurantService.findById(request.restaurantId());
        item.update(
                restaurant, request.name().trim(), request.description().trim(), request.price(),
                request.dineInOnly(), request.photoPath().trim()
        );
        return item;
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
