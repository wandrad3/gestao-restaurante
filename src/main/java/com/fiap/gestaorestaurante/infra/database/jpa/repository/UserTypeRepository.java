package com.fiap.gestaorestaurante.infra.database.jpa.repository;

import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
