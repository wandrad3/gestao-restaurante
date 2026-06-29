package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.core.controller.UserTypeCoreController;
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

    private final UserTypeCoreController controller;

    public UserTypeController(UserTypeCoreController controller) {
        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTypeResponse create(@Valid @RequestBody UserTypeRequest request) {
        return UserTypeResponse.from(controller.create(request.name()));
    }

    @GetMapping
    public List<UserTypeResponse> findAll() {
        return controller.findAll().stream().map(UserTypeResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserTypeResponse findById(@PathVariable Long id) {
        return UserTypeResponse.from(controller.findById(id));
    }

    @PutMapping("/{id}")
    public UserTypeResponse update(@PathVariable Long id, @Valid @RequestBody UserTypeRequest request) {
        return UserTypeResponse.from(controller.update(id, request.name()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        controller.delete(id);
    }
}
