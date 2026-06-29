package com.fiap.gestaorestaurante.infra.database.jpa.repository;

import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Override
    @EntityGraph(attributePaths = "userType")
    List<UserEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "userType")
    Optional<UserEntity> findById(Long id);

    @Query("select u from UserEntity u join fetch u.userType where lower(u.name) like lower(concat('%', :name, '%')) order by u.name")
    List<UserEntity> searchByName(@Param("name") String name);

    @EntityGraph(attributePaths = "userType")
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
}
