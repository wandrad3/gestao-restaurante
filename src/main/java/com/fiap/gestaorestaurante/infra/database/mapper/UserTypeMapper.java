package com.fiap.gestaorestaurante.infra.database.mapper;

import com.fiap.gestaorestaurante.core.domain.UserType;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper {
    public UserType map(UserTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserType(entity.getId(), entity.getName());
    }

    public UserTypeEntity map(UserType type) {
        if (type == null) {
            return null;
        }
        return new UserTypeEntity(type.getId(), type.getName());
    }
}
