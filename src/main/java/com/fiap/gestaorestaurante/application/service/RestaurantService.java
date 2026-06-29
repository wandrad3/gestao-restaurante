package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.Restaurant;
import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.infrastructure.persistence.MenuItemRepository;
import com.fiap.gestaorestaurante.infrastructure.persistence.RestaurantRepository;
import com.fiap.gestaorestaurante.infrastructure.web.dto.RestaurantRequest;
import com.fiap.gestaorestaurante.core.exception.ConflictException;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class RestaurantService {

    private final RestaurantRepository repository;
    private final MenuItemRepository menuItemRepository;
    private final UserService userService;

    public RestaurantService(RestaurantRepository repository, MenuItemRepository menuItemRepository,
                             UserService userService) {
        this.repository = repository;
        this.menuItemRepository = menuItemRepository;
        this.userService = userService;
    }

    public Restaurant create(RestaurantRequest request) {
        User owner = userService.findById(request.ownerId());
        return repository.save(new Restaurant(
                request.name().trim(), request.address().trim(), request.cuisineType().trim(),
                request.openingHours().trim(), owner
        ));
    }

    @Transactional(readOnly = true)
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Restaurant findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));
    }

    public Restaurant update(Long id, RestaurantRequest request) {
        Restaurant restaurant = findById(id);
        User owner = userService.findById(request.ownerId());
        restaurant.update(
                request.name().trim(), request.address().trim(), request.cuisineType().trim(),
                request.openingHours().trim(), owner
        );
        return restaurant;
    }

    public void delete(Long id) {
        if (menuItemRepository.existsByRestaurantId(id)) {
            throw new ConflictException("Restaurante possui itens de cardápio e não pode ser removido");
        }
        repository.delete(findById(id));
    }
}
