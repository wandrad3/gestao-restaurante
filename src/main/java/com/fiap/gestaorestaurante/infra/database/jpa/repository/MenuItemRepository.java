package com.fiap.gestaorestaurante.infra.database.jpa.repository;

import com.fiap.gestaorestaurante.infra.database.jpa.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    @Override
    @EntityGraph(attributePaths = "restaurant.owner.userType")
    List<MenuItemEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "restaurant.owner.userType")
    Optional<MenuItemEntity> findById(Long id);

    @EntityGraph(attributePaths = "restaurant.owner.userType")
    List<MenuItemEntity> findAllByRestaurantIdOrderByName(Long restaurantId);

    boolean existsByRestaurantId(Long restaurantId);
}
