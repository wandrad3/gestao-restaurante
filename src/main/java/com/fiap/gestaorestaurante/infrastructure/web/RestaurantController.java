package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.core.controller.RestaurantCoreController;
import com.fiap.gestaorestaurante.core.dto.RestaurantInputDto;
import com.fiap.gestaorestaurante.infrastructure.web.dto.RestaurantRequest;
import com.fiap.gestaorestaurante.infrastructure.web.dto.RestaurantResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantCoreController controller;

    public RestaurantController(RestaurantCoreController controller) {
        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse create(@Valid @RequestBody RestaurantRequest request) {
        return RestaurantResponse.from(controller.create(toInput(request)));
    }

    @GetMapping
    public List<RestaurantResponse> findAll() {
        return controller.findAll().stream().map(RestaurantResponse::from).toList();
    }

    @GetMapping("/{id}")
    public RestaurantResponse findById(@PathVariable Long id) {
        return RestaurantResponse.from(controller.findById(id));
    }

    @PutMapping("/{id}")
    public RestaurantResponse update(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) {
        return RestaurantResponse.from(controller.update(id, toInput(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        controller.delete(id);
    }

    private RestaurantInputDto toInput(RestaurantRequest request) {
        return new RestaurantInputDto(
                request.name(), request.address(), request.cuisineType(),
                request.openingHours(), request.ownerId()
        );
    }
}
