package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.domain.model.UserType;
import com.fiap.gestaorestaurante.infrastructure.persistence.RestaurantRepository;
import com.fiap.gestaorestaurante.infrastructure.persistence.UserRepository;
import com.fiap.gestaorestaurante.infrastructure.web.dto.UserRequest;
import com.fiap.gestaorestaurante.infrastructure.web.dto.UserUpdateRequest;
import com.fiap.gestaorestaurante.core.exception.ConflictException;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class UserService {

    private final UserRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserTypeService userTypeService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RestaurantRepository restaurantRepository,
                       UserTypeService userTypeService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userTypeService = userTypeService;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserRequest request) {
        ensureUnique(request.email(), request.username(), null);
        UserType type = userTypeService.findById(request.userTypeId());
        return repository.save(new User(
                request.name().trim(), request.email().trim(), request.username().trim(),
                passwordEncoder.encode(request.password()), request.street().trim(), request.number().trim(),
                request.city().trim(), request.state().toUpperCase(), request.zipCode(), type
        ));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<User> searchByName(String name) {
        return repository.searchByName(name == null ? "" : name.trim());
    }

    public User update(Long id, UserUpdateRequest request) {
        User user = findById(id);
        ensureUnique(request.email(), request.username(), id);
        UserType type = userTypeService.findById(request.userTypeId());
        user.update(
                request.name().trim(), request.email().trim(), request.username().trim(),
                request.street().trim(), request.number().trim(), request.city().trim(),
                request.state().toUpperCase(), request.zipCode(), type
        );
        return user;
    }

    public void changePassword(Long id, String password) {
        findById(id).changePassword(passwordEncoder.encode(password));
    }

    public void delete(Long id) {
        if (restaurantRepository.existsByOwnerId(id)) {
            throw new ConflictException("Usuário é dono de um restaurante e não pode ser removido");
        }
        repository.delete(findById(id));
    }

    private void ensureUnique(String email, String username, Long ignoredId) {
        boolean emailExists = ignoredId == null
                ? repository.existsByEmailIgnoreCase(email)
                : repository.existsByEmailIgnoreCaseAndIdNot(email, ignoredId);
        boolean usernameExists = ignoredId == null
                ? repository.existsByUsernameIgnoreCase(username)
                : repository.existsByUsernameIgnoreCaseAndIdNot(username, ignoredId);
        if (emailExists) {
            throw new ConflictException("E-mail já cadastrado");
        }
        if (usernameExists) {
            throw new ConflictException("Nome de login já cadastrado");
        }
    }
}
