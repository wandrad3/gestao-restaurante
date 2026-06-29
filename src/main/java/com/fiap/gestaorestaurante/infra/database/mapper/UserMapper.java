package com.fiap.gestaorestaurante.infra.database.mapper;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final UserTypeMapper userTypeMapper;

    public UserMapper(UserTypeMapper userTypeMapper) {
        this.userTypeMapper = userTypeMapper;
    }

    public User map(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(), entity.getName(), entity.getEmail(), entity.getUsername(),
                entity.getPassword(), entity.getStreet(), entity.getNumber(), entity.getCity(),
                entity.getState(), entity.getZipCode(), userTypeMapper.map(entity.getUserType()),
                entity.getUpdatedAt()
        );
    }

    public UserEntity map(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.getId(), user.getName(), user.getEmail(), user.getUsername(),
                user.getPassword(), user.getStreet(), user.getNumber(), user.getCity(),
                user.getState(), user.getZipCode(), userTypeMapper.map(user.getUserType()),
                user.getUpdatedAt()
        );
    }
}
