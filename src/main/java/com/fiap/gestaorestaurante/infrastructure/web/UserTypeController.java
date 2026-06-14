package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.application.service.UserTypeService;
import com.fiap.gestaorestaurante.infrastructure.web.dto.UserTypeRequest;
import com.fiap.gestaorestaurante.infrastructure.web.dto.UserTypeResponse;
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
@RequestMapping("/api/v1/user-types")
public class UserTypeController {

    private final UserTypeService service;

    public UserTypeController(UserTypeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTypeResponse create(@Valid @RequestBody UserTypeRequest request) {
        return UserTypeResponse.from(service.create(request.name()));
    }

    @GetMapping
    public List<UserTypeResponse> findAll() {
        return service.findAll().stream().map(UserTypeResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserTypeResponse findById(@PathVariable Long id) {
        return UserTypeResponse.from(service.findById(id));
    }

    @PutMapping("/{id}")
    public UserTypeResponse update(@PathVariable Long id, @Valid @RequestBody UserTypeRequest request) {
        return UserTypeResponse.from(service.update(id, request.name()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
