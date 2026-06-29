package com.fiap.gestaorestaurante.infrastructure.persistence;

import com.fiap.gestaorestaurante.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Override
    @EntityGraph(attributePaths = "owner")
    List<Restaurant> findAll();

    @Override
    @EntityGraph(attributePaths = "owner")
    Optional<Restaurant> findById(Long id);

    boolean existsByOwnerId(Long ownerId);
}
