package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.domain.UserType;
import com.fiap.gestaorestaurante.core.dto.UserInputDto;
import com.fiap.gestaorestaurante.core.dto.UserUpdateInputDto;
import com.fiap.gestaorestaurante.core.exception.ConflictException;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import com.fiap.gestaorestaurante.core.gateway.PasswordGateway;
import com.fiap.gestaorestaurante.core.gateway.RestaurantGateway;
import com.fiap.gestaorestaurante.core.gateway.UserGateway;

import java.util.List;

public class UserUsecase {
    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserTypeUsecase userTypeUsecase;
    private final PasswordGateway passwordGateway;

    public UserUsecase(UserGateway userGateway, RestaurantGateway restaurantGateway,
                       UserTypeUsecase userTypeUsecase, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.restaurantGateway = restaurantGateway;
        this.userTypeUsecase = userTypeUsecase;
        this.passwordGateway = passwordGateway;
    }

    public User create(UserInputDto input) {
        ensureUnique(input.email(), input.username(), null);
        UserType type = userTypeUsecase.findById(input.userTypeId());
        return userGateway.save(new User(
                input.name().trim(), input.email().trim(), input.username().trim(),
                passwordGateway.encode(input.password()), input.street().trim(), input.number().trim(),
                input.city().trim(), input.state().toUpperCase(), input.zipCode(), type
        ));
    }

    public List<User> findAll() {
        return userGateway.findAll();
    }

    public User findById(Long id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    public List<User> searchByName(String name) {
        return userGateway.searchByName(name == null ? "" : name.trim());
    }

    public User update(Long id, UserUpdateInputDto input) {
        User user = findById(id);
        ensureUnique(input.email(), input.username(), id);
        UserType type = userTypeUsecase.findById(input.userTypeId());
        user.update(
                input.name().trim(), input.email().trim(), input.username().trim(),
                input.street().trim(), input.number().trim(), input.city().trim(),
                input.state().toUpperCase(), input.zipCode(), type
        );
        return userGateway.save(user);
    }

    public void changePassword(Long id, String password) {
        User user = findById(id);
        user.changePassword(passwordGateway.encode(password));
        userGateway.save(user);
    }

    public void delete(Long id) {
        if (restaurantGateway.existsByOwnerId(id)) {
            throw new ConflictException("Usuário é dono de um restaurante e não pode ser removido");
        }
        userGateway.delete(findById(id));
    }

    private void ensureUnique(String email, String username, Long ignoredId) {
        boolean emailExists = ignoredId == null
                ? userGateway.existsByEmailIgnoreCase(email)
                : userGateway.existsByEmailIgnoreCaseAndIdNot(email, ignoredId);
        boolean usernameExists = ignoredId == null
                ? userGateway.existsByUsernameIgnoreCase(username)
                : userGateway.existsByUsernameIgnoreCaseAndIdNot(username, ignoredId);
        if (emailExists) {
            throw new ConflictException("E-mail já cadastrado");
        }
        if (usernameExists) {
            throw new ConflictException("Nome de login já cadastrado");
        }
    }
}
