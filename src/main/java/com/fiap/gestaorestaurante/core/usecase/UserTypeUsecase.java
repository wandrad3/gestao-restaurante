package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.UserType;
import com.fiap.gestaorestaurante.core.exception.ConflictException;
import com.fiap.gestaorestaurante.core.exception.ResourceNotFoundException;
import com.fiap.gestaorestaurante.core.gateway.UserTypeGateway;

import java.util.List;

public class UserTypeUsecase {
    private final UserTypeGateway gateway;

    public UserTypeUsecase(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    public UserType create(String name) {
        ensureUnique(name);
        return gateway.save(new UserType(name.trim()));
    }

    public List<UserType> findAll() {
        return gateway.findAll();
    }

    public UserType findById(Long id) {
        return gateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + id));
    }

    public UserType update(Long id, String name) {
        UserType type = findById(id);
        if (!type.getName().equalsIgnoreCase(name) && gateway.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Já existe um tipo de usuário com esse nome");
        }
        type.update(name.trim());
        return gateway.save(type);
    }

    public void delete(Long id) {
        gateway.delete(findById(id));
    }

    private void ensureUnique(String name) {
        if (gateway.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Já existe um tipo de usuário com esse nome");
        }
    }
}
