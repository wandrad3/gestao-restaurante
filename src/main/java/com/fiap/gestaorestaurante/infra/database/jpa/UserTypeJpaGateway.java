package com.fiap.gestaorestaurante.infra.database.jpa;

import com.fiap.gestaorestaurante.core.domain.UserType;
import com.fiap.gestaorestaurante.core.gateway.UserTypeGateway;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.UserTypeRepository;
import com.fiap.gestaorestaurante.infra.database.mapper.UserTypeMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserTypeJpaGateway implements UserTypeGateway {
    private final UserTypeRepository repository;
    private final UserTypeMapper mapper;

    public UserTypeJpaGateway(UserTypeRepository repository, UserTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserType save(UserType type) {
        return mapper.map(repository.save(mapper.map(type)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserType> findAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserType> findById(Long id) {
        return repository.findById(id).map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    @Override
    @Transactional
    public void delete(UserType type) {
        repository.delete(mapper.map(type));
    }
}
