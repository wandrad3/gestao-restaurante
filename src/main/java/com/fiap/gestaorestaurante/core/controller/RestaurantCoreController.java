package com.fiap.gestaorestaurante.core.controller;

import com.fiap.gestaorestaurante.core.domain.Restaurant;
import com.fiap.gestaorestaurante.core.dto.RestaurantInputDto;
import com.fiap.gestaorestaurante.core.usecase.RestaurantUsecase;

import java.util.List;

public class RestaurantCoreController {
    private final RestaurantUsecase usecase;

    public RestaurantCoreController(RestaurantUsecase usecase) {
        this.usecase = usecase;
    }

    public Restaurant create(RestaurantInputDto input) { return usecase.create(input); }
    public List<Restaurant> findAll() { return usecase.findAll(); }
    public Restaurant findById(Long id) { return usecase.findById(id); }
    public Restaurant update(Long id, RestaurantInputDto input) { return usecase.update(id, input); }
    public void delete(Long id) { usecase.delete(id); }
}
