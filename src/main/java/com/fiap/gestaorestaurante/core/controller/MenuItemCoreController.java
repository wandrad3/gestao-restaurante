package com.fiap.gestaorestaurante.core.controller;

import com.fiap.gestaorestaurante.core.domain.MenuItem;
import com.fiap.gestaorestaurante.core.dto.MenuItemInputDto;
import com.fiap.gestaorestaurante.core.usecase.MenuItemUsecase;

import java.util.List;

public class MenuItemCoreController {
    private final MenuItemUsecase usecase;

    public MenuItemCoreController(MenuItemUsecase usecase) {
        this.usecase = usecase;
    }

    public MenuItem create(MenuItemInputDto input) { return usecase.create(input); }
    public List<MenuItem> findAll(Long restaurantId) { return usecase.findAll(restaurantId); }
    public MenuItem findById(Long id) { return usecase.findById(id); }
    public MenuItem update(Long id, MenuItemInputDto input) { return usecase.update(id, input); }
    public void delete(Long id) { usecase.delete(id); }
}
