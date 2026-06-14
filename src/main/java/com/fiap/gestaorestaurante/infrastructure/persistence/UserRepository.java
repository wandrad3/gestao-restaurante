package com.fiap.gestaorestaurante.infrastructure.persistence;

import com.fiap.gestaorestaurante.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = "userType")
    List<User> findAll();

    @Override
    @EntityGraph(attributePaths = "userType")
    Optional<User> findById(Long id);

    @Query("select u from User u join fetch u.userType where lower(u.name) like lower(concat('%', :name, '%')) order by u.name")
    List<User> searchByName(@Param("name") String name);

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
}
