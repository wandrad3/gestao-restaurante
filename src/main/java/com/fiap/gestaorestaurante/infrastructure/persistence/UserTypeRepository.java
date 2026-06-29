package com.fiap.gestaorestaurante.infrastructure.persistence;

import com.fiap.gestaorestaurante.domain.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    boolean existsByNameIgnoreCase(String name);
}
