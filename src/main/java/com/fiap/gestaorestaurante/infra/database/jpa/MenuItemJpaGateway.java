package com.fiap.gestaorestaurante.infra.database.jpa;

import com.fiap.gestaorestaurante.core.domain.MenuItem;
import com.fiap.gestaorestaurante.core.gateway.MenuItemGateway;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.MenuItemEntity;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.MenuItemRepository;
import com.fiap.gestaorestaurante.infra.database.jpa.repository.RestaurantRepository;
import com.fiap.gestaorestaurante.infra.database.mapper.MenuItemMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class MenuItemJpaGateway implements MenuItemGateway {
    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemMapper mapper;

    public MenuItemJpaGateway(MenuItemRepository repository, RestaurantRepository restaurantRepository,
                              MenuItemMapper mapper) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public MenuItem save(MenuItem item) {
        var restaurant = restaurantRepository.getReferenceById(item.getRestaurant().getId());
        var entity = new MenuItemEntity(
                item.getId(), restaurant, item.getName(), item.getDescription(),
                item.getPrice(), item.isDineInOnly(), item.getPhotoPath()
        );
        return mapper.map(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findAllByRestaurantIdOrderByName(Long restaurantId) {
        return repository.findAllByRestaurantIdOrderByName(restaurantId).stream().map(mapper::map).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuItem> findById(Long id) {
        return repository.findById(id).map(mapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRestaurantId(Long restaurantId) {
        return repository.existsByRestaurantId(restaurantId);
    }

    @Override
    @Transactional
    public void delete(MenuItem item) {
        repository.delete(mapper.map(item));
    }
}
