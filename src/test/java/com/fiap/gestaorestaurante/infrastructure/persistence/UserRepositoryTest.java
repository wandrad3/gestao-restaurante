package com.fiap.gestaorestaurante.infra.database.jpa.repository;

import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserEntity;
import com.fiap.gestaorestaurante.infra.database.jpa.entity.UserTypeEntity;
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
        UserTypeEntity client = userTypeRepository.save(new UserTypeEntity(null, "Cliente"));
        userRepository.save(new UserEntity(
                null, "Maria da Silva", "maria@example.com", "maria", "encoded-password",
                "Rua A", "10", "São Paulo", "SP", "01001-000", client, null
        ));
        userRepository.save(new UserEntity(
                null, "João Souza", "joao@example.com", "joao", "encoded-password",
                "Rua B", "20", "São Paulo", "SP", "01002-000", client, null
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

    private void assertNames(List<UserEntity> users, String... names) {
        assertThat(users).extracting(UserEntity::getName).containsExactly(names);
    }
}
