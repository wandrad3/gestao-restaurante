package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.UserType;
import com.fiap.gestaorestaurante.infrastructure.persistence.UserTypeRepository;
import com.fiap.gestaorestaurante.infrastructure.web.exception.ConflictException;
import com.fiap.gestaorestaurante.infrastructure.web.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserTypeService {

    private final UserTypeRepository repository;

    public UserTypeService(UserTypeRepository repository) {
        this.repository = repository;
    }

    public UserType create(String name) {
        ensureUnique(name);
        return repository.save(new UserType(name.trim()));
    }

    @Transactional(readOnly = true)
    public List<UserType> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public UserType findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + id));
    }

    public UserType update(Long id, String name) {
        UserType type = findById(id);
        if (!type.getName().equalsIgnoreCase(name) && repository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Já existe um tipo de usuário com esse nome");
        }
        type.update(name.trim());
        return type;
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private void ensureUnique(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Já existe um tipo de usuário com esse nome");
        }
    }
}
