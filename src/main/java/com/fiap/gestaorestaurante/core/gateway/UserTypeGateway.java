package com.fiap.gestaorestaurante.core.gateway;

import com.fiap.gestaorestaurante.core.domain.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypeGateway {
    UserType save(UserType type);
    List<UserType> findAll();
    Optional<UserType> findById(Long id);
    boolean existsByNameIgnoreCase(String name);
    void delete(UserType type);
}
