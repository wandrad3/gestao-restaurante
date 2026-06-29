package com.fiap.gestaorestaurante.infra.database.jpa;

import com.fiap.gestaorestaurante.core.domain.Restaurant;
import com.fiap.gestaorestaurante.core.gateway.RestaurantGateway;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.RestaurantEntity;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.RestaurantRepository;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.UserRepository;
import com.fiap.gestaorestaurante.infra.database.mapper.RestaurantMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantJpaGateway implements RestaurantGateway {
    private final RestaurantRepository repository;
    private final UserRepository userRepository;
    private final RestaurantMapper mapper;

    public RestaurantJpaGateway(RestaurantRepository repository, UserRepository userRepository,
                                RestaurantMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        var owner = userRepository.getReferenceById(restaurant.getOwner().getId());
        var entity = new RestaurantEntity(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(), owner
        );
        return mapper.map(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Restaurant> findAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Restaurant> findById(Long id) {
        return repository.findById(id).map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByOwnerId(Long ownerId) {
        return repository.existsByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public void delete(Restaurant restaurant) {
        repository.delete(mapper.map(restaurant));
    }
}
