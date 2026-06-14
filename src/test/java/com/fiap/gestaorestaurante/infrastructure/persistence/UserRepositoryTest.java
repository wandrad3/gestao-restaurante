package com.fiap.gestaorestaurante.infrastructure.persistence;

import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @BeforeEach
    void setUp() {
        UserType client = userTypeRepository.save(new UserType("Cliente"));
        userRepository.save(new User(
                "Maria da Silva", "maria@example.com", "maria", "encoded-password",
                "Rua A", "10", "São Paulo", "SP", "01001-000", client
        ));
        userRepository.save(new User(
                "João Souza", "joao@example.com", "joao", "encoded-password",
                "Rua B", "20", "São Paulo", "SP", "01002-000", client
        ));
    }

    @Test
    void shouldFindExactName() {
        assertNames(userRepository.searchByName("Maria da Silva"), "Maria da Silva");
    }

    @Test
    void shouldFindPartialNameAnywhere() {
        assertNames(userRepository.searchByName("da Sil"), "Maria da Silva");
    }

    @Test
    void shouldIgnoreCase() {
        assertNames(userRepository.searchByName("mARIA DA sILVA"), "Maria da Silva");
    }

    @Test
    void shouldReturnEmptyListWhenNameDoesNotExist() {
        assertThat(userRepository.searchByName("Inexistente")).isEmpty();
    }

    private void assertNames(List<User> users, String... names) {
        assertThat(users).extracting(User::getName).containsExactly(names);
    }
}
