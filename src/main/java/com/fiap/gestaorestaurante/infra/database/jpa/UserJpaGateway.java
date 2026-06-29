package com.fiap.gestaorestaurante.infra.database.jpa;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.gateway.UserGateway;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserEntity;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.UserRepository;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.UserTypeRepository;
import com.fiap.gestaorestaurante.infra.database.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserJpaGateway implements UserGateway {
    private final UserRepository repository;
    private final UserTypeRepository userTypeRepository;
    private final UserMapper mapper;

    public UserJpaGateway(UserRepository repository, UserTypeRepository userTypeRepository, UserMapper mapper) {
        this.repository = repository;
        this.userTypeRepository = userTypeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public User save(User user) {
        var userType = userTypeRepository.getReferenceById(user.getUserType().getId());
        var entity = new UserEntity(
                user.getId(), user.getName(), user.getEmail(), user.getUsername(),
                user.getPassword(), user.getStreet(), user.getNumber(), user.getCity(),
                user.getState(), user.getZipCode(), userType, user.getUpdatedAt()
        );
        return mapper.map(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchByName(String name) {
        return repository.searchByName(name).stream().map(mapper::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmailIgnoreCase(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsernameIgnoreCase(String username) {
        return repository.existsByUsernameIgnoreCase(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id) {
        return repository.existsByEmailIgnoreCaseAndIdNot(email, id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id) {
        return repository.existsByUsernameIgnoreCaseAndIdNot(username, id);
    }

    @Override
    @Transactional
    public void delete(User user) {
        repository.delete(mapper.map(user));
    }
}
