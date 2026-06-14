package com.fiap.gestaorestaurante.application.service;

import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.infrastructure.persistence.RestaurantRepository;
import com.fiap.gestaorestaurante.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private UserTypeService userTypeService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(
                userRepository, restaurantRepository, userTypeService, passwordEncoder
        );
    }

    @Test
    void shouldTrimNameAndReturnRepositoryResults() {
        User maria = org.mockito.Mockito.mock(User.class);
        when(userRepository.searchByName("maria")).thenReturn(List.of(maria));

        assertThat(service.searchByName("  maria  ")).containsExactly(maria);
        verify(userRepository).searchByName("maria");
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryFindsNothing() {
        when(userRepository.searchByName("inexistente")).thenReturn(List.of());

        assertThat(service.searchByName("inexistente")).isEmpty();
    }
}
