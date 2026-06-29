package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.application.service.RestaurantService;
import com.fiap.gestaorestaurante.infrastructure.web.dto.RestaurantRequest;
import com.fiap.gestaorestaurante.infrastructure.web.dto.RestaurantResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse create(@Valid @RequestBody RestaurantRequest request) {
        return RestaurantResponse.from(service.create(request));
    }

    @GetMapping
    public List<RestaurantResponse> findAll() {
        return service.findAll().stream().map(RestaurantResponse::from).toList();
    }

    @GetMapping("/{id}")
    public RestaurantResponse findById(@PathVariable Long id) {
        return RestaurantResponse.from(service.findById(id));
    }

    @PutMapping("/{id}")
    public RestaurantResponse update(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) {
        return RestaurantResponse.from(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
