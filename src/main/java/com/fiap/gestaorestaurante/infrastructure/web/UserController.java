package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.application.service.AuthService;
import com.fiap.gestaorestaurante.application.service.TokenService;
import com.fiap.gestaorestaurante.application.service.UserService;
import com.fiap.gestaorestaurante.domain.model.Token;
import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.infrastructure.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    TokenService tokenService;

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return UserResponse.from(service.create(request));
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return service.findAll().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return UserResponse.from(service.findById(id));
    }

    @GetMapping("/search")
    public List<UserResponse> searchByName(@RequestParam String name) {
        return service.searchByName(name).stream().map(UserResponse::from).toList();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return UserResponse.from(service.update(id, request));
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long id, @Valid @RequestBody PasswordRequest request) {
        service.changePassword(id, request.password());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }




    @PostMapping("/login")
    @Operation(
            summary = "Realiza login do usuario e gera token JWT",
            description = "Autentica o usuario com email e senha. Retorna um token JWT se as credenciais estiverem corretas."
    )
    public String login(@RequestBody Credentials credentials){
        User user = authService.autenticar(credentials.email(), credentials.password());
        return tokenService.createToken(user);
    }
}
