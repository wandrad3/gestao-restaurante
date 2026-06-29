package com.fiap.gestaorestaurante.core.gateway;

import com.fiap.gestaorestaurante.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    List<User> searchByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
    void delete(User user);
}
