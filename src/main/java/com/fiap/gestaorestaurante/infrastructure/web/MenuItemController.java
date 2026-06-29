package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.core.controller.MenuItemCoreController;
import com.fiap.gestaorestaurante.core.dto.MenuItemInputDto;
import com.fiap.gestaorestaurante.infrastructure.web.dto.MenuItemRequest;
import com.fiap.gestaorestaurante.infrastructure.web.dto.MenuItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemController {

    private final MenuItemCoreController controller;

    public MenuItemController(MenuItemCoreController controller) {
        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemResponse create(@Valid @RequestBody MenuItemRequest request) {
        return MenuItemResponse.from(controller.create(toInput(request)));
    }

    @GetMapping
    public List<MenuItemResponse> findAll(@RequestParam(required = false) Long restaurantId) {
        return controller.findAll(restaurantId).stream().map(MenuItemResponse::from).toList();
    }

    @GetMapping("/{id}")
    public MenuItemResponse findById(@PathVariable Long id) {
        return MenuItemResponse.from(controller.findById(id));
    }

    @PutMapping("/{id}")
    public MenuItemResponse update(@PathVariable Long id, @Valid @RequestBody MenuItemRequest request) {
        return MenuItemResponse.from(controller.update(id, toInput(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        controller.delete(id);
    }

    private MenuItemInputDto toInput(MenuItemRequest request) {
        return new MenuItemInputDto(
                request.restaurantId(), request.name(), request.description(), request.price(),
                request.dineInOnly(), request.photoPath()
        );
    }
}
