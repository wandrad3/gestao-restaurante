package com.fiap.gestaorestaurante.infra.database.mapper;

import com.fiap.gestaorestaurante.core.domain.Restaurant;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    private final UserMapper userMapper;

    public RestaurantMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Restaurant map(RestaurantEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Restaurant(
                entity.getId(), entity.getName(), entity.getAddress(), entity.getCuisineType(),
                entity.getOpeningHours(), userMapper.map(entity.getOwner())
        );
    }

    public RestaurantEntity map(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        return new RestaurantEntity(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(),
                userMapper.map(restaurant.getOwner())
        );
    }
}
