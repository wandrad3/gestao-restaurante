package com.fiap.gestaorestaurante.infra.config;

import com.fiap.gestaorestaurante.core.controller.MenuItemCoreController;
import com.fiap.gestaorestaurante.core.controller.RestaurantCoreController;
import com.fiap.gestaorestaurante.core.controller.UserCoreController;
import com.fiap.gestaorestaurante.core.controller.UserTypeCoreController;
import com.fiap.gestaorestaurante.core.gateway.MenuItemGateway;
import com.fiap.gestaorestaurante.core.gateway.PasswordGateway;
import com.fiap.gestaorestaurante.core.gateway.RestaurantGateway;
import com.fiap.gestaorestaurante.core.gateway.UserGateway;
import com.fiap.gestaorestaurante.core.gateway.UserTypeGateway;
import com.fiap.gestaorestaurante.core.usecase.AuthUsecase;
import com.fiap.gestaorestaurante.core.usecase.MenuItemUsecase;
import com.fiap.gestaorestaurante.core.usecase.RestaurantUsecase;
import com.fiap.gestaorestaurante.core.usecase.UserTypeUsecase;
import com.fiap.gestaorestaurante.core.usecase.UserUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjecaoDependenciaConfiguration {
    @Bean
    UserTypeUsecase userTypeUsecase(UserTypeGateway userTypeGateway) {
        return new UserTypeUsecase(userTypeGateway);
    }

    @Bean
    UserUsecase userUsecase(UserGateway userGateway, RestaurantGateway restaurantGateway,
                            UserTypeUsecase userTypeUsecase, PasswordGateway passwordGateway) {
        return new UserUsecase(userGateway, restaurantGateway, userTypeUsecase, passwordGateway);
    }

    @Bean
    RestaurantUsecase restaurantUsecase(RestaurantGateway restaurantGateway, MenuItemGateway menuItemGateway,
                                        UserUsecase userUsecase) {
        return new RestaurantUsecase(restaurantGateway, menuItemGateway, userUsecase);
    }

    @Bean
    MenuItemUsecase menuItemUsecase(MenuItemGateway menuItemGateway, RestaurantUsecase restaurantUsecase) {
        return new MenuItemUsecase(menuItemGateway, restaurantUsecase);
    }

    @Bean
    AuthUsecase authUsecase(UserGateway userGateway, PasswordGateway passwordGateway) {
        return new AuthUsecase(userGateway, passwordGateway);
    }

    @Bean
    UserCoreController userCoreController(UserUsecase usecase) {
        return new UserCoreController(usecase);
    }

    @Bean
    UserTypeCoreController userTypeCoreController(UserTypeUsecase usecase) {
        return new UserTypeCoreController(usecase);
    }

    @Bean
    RestaurantCoreController restaurantCoreController(RestaurantUsecase usecase) {
        return new RestaurantCoreController(usecase);
    }

    @Bean
    MenuItemCoreController menuItemCoreController(MenuItemUsecase usecase) {
        return new MenuItemCoreController(usecase);
    }
}
