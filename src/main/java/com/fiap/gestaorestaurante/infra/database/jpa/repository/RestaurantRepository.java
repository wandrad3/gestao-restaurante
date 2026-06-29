package com.fiap.gestaorestaurante.infra.database.jpa.repository;

import com.fiap.gestaorestaurante.infra.database.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Override
    @EntityGraph(attributePaths = "owner.userType")
    List<RestaurantEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "owner.userType")
    Optional<RestaurantEntity> findById(Long id);

    boolean existsByOwnerId(Long ownerId);
}
