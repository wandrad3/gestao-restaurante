package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.core.controller.UserCoreController;
import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.dto.UserInputDto;
import com.fiap.gestaorestaurante.core.dto.UserUpdateInputDto;
import com.fiap.gestaorestaurante.core.usecase.AuthUsecase;
import com.fiap.gestaorestaurante.infra.security.TokenService;
import com.fiap.gestaorestaurante.infrastructure.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    private final UserCoreController controller;
    private final AuthUsecase authUsecase;
    private final TokenService tokenService;

    public UserController(UserCoreController controller, AuthUsecase authUsecase, TokenService tokenService) {
        this.controller = controller;
        this.authUsecase = authUsecase;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return UserResponse.from(controller.create(toInput(request)));
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return controller.findAll().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return UserResponse.from(controller.findById(id));
    }

    @GetMapping("/search")
    public List<UserResponse> searchByName(@RequestParam String name) {
        return controller.searchByName(name).stream().map(UserResponse::from).toList();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return UserResponse.from(controller.update(id, toInput(request)));
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long id, @Valid @RequestBody PasswordRequest request) {
        controller.changePassword(id, request.password());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        controller.delete(id);
    }




    @PostMapping("/login")
    @Operation(
            summary = "Realiza login do usuario e gera token JWT",
            description = "Autentica o usuario com email e senha. Retorna um token JWT se as credenciais estiverem corretas."
    )
    public String login(@RequestBody Credentials credentials){
        User user = authUsecase.autenticar(credentials.email(), credentials.password());
        return tokenService.createToken(user);
    }

    private UserInputDto toInput(UserRequest request) {
        return new UserInputDto(
                request.name(), request.email(), request.username(), request.password(),
                request.street(), request.number(), request.city(), request.state(),
                request.zipCode(), request.userTypeId()
        );
    }

    private UserUpdateInputDto toInput(UserUpdateRequest request) {
        return new UserUpdateInputDto(
                request.name(), request.email(), request.username(), request.street(),
                request.number(), request.city(), request.state(), request.zipCode(),
                request.userTypeId()
        );
    }
}
