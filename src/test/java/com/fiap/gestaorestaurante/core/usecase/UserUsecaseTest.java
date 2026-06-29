package com.fiap.gestaorestaurante.core.usecase;

import com.fiap.gestaorestaurante.core.domain.User;
import com.fiap.gestaorestaurante.core.gateway.PasswordGateway;
import com.fiap.gestaorestaurante.core.gateway.RestaurantGateway;
import com.fiap.gestaorestaurante.core.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUsecaseTest {
    @Mock
    private UserGateway userGateway;
    @Mock
    private RestaurantGateway restaurantGateway;
    @Mock
    private UserTypeUsecase userTypeUsecase;
    @Mock
    private PasswordGateway passwordGateway;

    private UserUsecase usecase;

    @BeforeEach
    void setUp() {
        usecase = new UserUsecase(userGateway, restaurantGateway, userTypeUsecase, passwordGateway);
    }

    @Test
    void shouldTrimNameAndReturnGatewayResults() {
        User maria = org.mockito.Mockito.mock(User.class);
        when(userGateway.searchByName("maria")).thenReturn(List.of(maria));

        assertThat(usecase.searchByName("  maria  ")).containsExactly(maria);
        verify(userGateway).searchByName("maria");
    }

    @Test
    void shouldReturnEmptyListWhenGatewayFindsNothing() {
        when(userGateway.searchByName("inexistente")).thenReturn(List.of());

        assertThat(usecase.searchByName("inexistente")).isEmpty();
    }
}
