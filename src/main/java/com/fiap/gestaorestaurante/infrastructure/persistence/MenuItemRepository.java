package com.fiap.gestaorestaurante.infrastructure.persistence;

import com.fiap.gestaorestaurante.domain.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Override
    @EntityGraph(attributePaths = "restaurant")
    List<MenuItem> findAll();

    @Override
    @EntityGraph(attributePaths = "restaurant")
    Optional<MenuItem> findById(Long id);

    @EntityGraph(attributePaths = "restaurant")
    List<MenuItem> findAllByRestaurantIdOrderByName(Long restaurantId);
    boolean existsByRestaurantId(Long restaurantId);
}
