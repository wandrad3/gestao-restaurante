package com.fiap.gestaorestaurante.infra.database.mapper;

import com.fiap.gestaorestaurante.core.domain.MenuItem;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.MenuItemEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    private final RestaurantMapper restaurantMapper;

    public MenuItemMapper(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public MenuItem map(MenuItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new MenuItem(
                entity.getId(), restaurantMapper.map(entity.getRestaurant()), entity.getName(),
                entity.getDescription(), entity.getPrice(), entity.isDineInOnly(), entity.getPhotoPath()
        );
    }

    public MenuItemEntity map(MenuItem item) {
        if (item == null) {
            return null;
        }
        return new MenuItemEntity(
                item.getId(), restaurantMapper.map(item.getRestaurant()), item.getName(),
                item.getDescription(), item.getPrice(), item.isDineInOnly(), item.getPhotoPath()
        );
    }
}
