package com.fiap.gestaorestaurante.infrastructure.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldExecuteCompleteCrudFlow() throws Exception {
        long ownerTypeId = createUserType("Dono de Restaurante");
        long clientTypeId = createUserType("Cliente");

        mockMvc.perform(get("/api/v1/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        mockMvc.perform(get("/api/v1/user-types/{id}", ownerTypeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dono de Restaurante"));
        mockMvc.perform(put("/api/v1/user-types/{id}", clientTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Consumidor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Consumidor"));

        long userId = createUser(ownerTypeId, "Maria da Silva", "maria@example.com", "maria");

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maria da Silva"));
        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria@example.com"));
        mockMvc.perform(get("/api/v1/users/search").param("name", "Maria da Silva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maria da Silva"));
        mockMvc.perform(get("/api/v1/users/search").param("name", "ria da"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maria da Silva"));
        mockMvc.perform(get("/api/v1/users/search").param("name", "RIA DA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maria da Silva"));
        mockMvc.perform(get("/api/v1/users/search").param("name", "inexistente"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        String updatedUser = """
                {
                  "name":"Maria Souza",
                  "email":"maria.souza@example.com",
                  "username":"maria.souza",
                  "street":"Rua B",
                  "number":"20",
                  "city":"Sao Paulo",
                  "state":"sp",
                  "zipCode":"01002-000",
                  "userTypeId":%d
                }
                """.formatted(ownerTypeId);
        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("SP"));
        mockMvc.perform(patch("/api/v1/users/{id}/password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"NovaSenha123\"}"))
                .andExpect(status().isNoContent());

        long restaurantId = createRestaurant(userId);
        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ownerId").value(userId));
        mockMvc.perform(get("/api/v1/restaurants/{id}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina FIAP"));
        mockMvc.perform(put("/api/v1/restaurants/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson(userId, "Cantina Centro")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina Centro"));

        long menuItemId = createMenuItem(restaurantId);
        mockMvc.perform(get("/api/v1/menu-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lasanha"));
        mockMvc.perform(get("/api/v1/menu-items").param("restaurantId", String.valueOf(restaurantId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurantId").value(restaurantId));
        mockMvc.perform(get("/api/v1/menu-items/{id}", menuItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(39.90));
        mockMvc.perform(put("/api/v1/menu-items/{id}", menuItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson(restaurantId, "Lasanha Grande", "59.90", true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dineInOnly").value(true));

        mockMvc.perform(delete("/api/v1/restaurants/{id}", restaurantId))
                .andExpect(status().isConflict());
        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isConflict());

        mockMvc.perform(delete("/api/v1/menu-items/{id}", menuItemId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/restaurants/{id}", restaurantId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/user-types/{id}", ownerTypeId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/user-types/{id}", clientTypeId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnValidationNotFoundAndConflictErrors() throws Exception {
        mockMvc.perform(post("/api/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.name").exists());

        long typeId = createUserType("Cliente");
        mockMvc.perform(post("/api/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"cliente\"}"))
                .andExpect(status().isConflict());

        long secondTypeId = createUserType("Dono");
        mockMvc.perform(put("/api/v1/user-types/{id}", secondTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"CLIENTE\"}"))
                .andExpect(status().isConflict());

        mockMvc.perform(get("/api/v1/user-types/999999"))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/api/v1/user-types/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Novo\"}"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/v1/user-types/999999"))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson(999999, "Sem Tipo", "sem.tipo@example.com", "semtipo")))
                .andExpect(status().isNotFound());

        long userId = createUser(typeId, "Joao Silva", "joao@example.com", "joao");
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson(typeId, "Outro", "JOAO@example.com", "outro")))
                .andExpect(status().isConflict());
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson(typeId, "Outro", "outro@example.com", "JOAO")))
                .andExpect(status().isConflict());
        mockMvc.perform(get("/api/v1/users/999999")).andExpect(status().isNotFound());
        mockMvc.perform(patch("/api/v1/users/{id}/password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"curta\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson(999999, "Sem Dono")))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/api/v1/restaurants/999999")).andExpect(status().isNotFound());

        mockMvc.perform(post("/api/v1/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson(999999, "Sem Restaurante", "10.00", false)))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/api/v1/menu-items").param("restaurantId", "999999"))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/api/v1/menu-items/999999")).andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/v1/menu-items/999999")).andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/v1/users/{id}", userId)).andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/user-types/{id}", typeId)).andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/user-types/{id}", secondTypeId)).andExpect(status().isNoContent());
    }

    private long createUserType(String name) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        return idFrom(result);
    }

    private long createUser(long typeId, String name, String email, String username) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson(typeId, name, email, username)))
                .andExpect(status().isCreated())
                .andReturn();
        return idFrom(result);
    }

    private long createRestaurant(long ownerId) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson(ownerId, "Cantina FIAP")))
                .andExpect(status().isCreated())
                .andReturn();
        return idFrom(result);
    }

    private long createMenuItem(long restaurantId) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson(restaurantId, "Lasanha", "39.90", false)))
                .andExpect(status().isCreated())
                .andReturn();
        return idFrom(result);
    }

    private long idFrom(MvcResult result) throws Exception {
        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
        return response.get("id").asLong();
    }

    private String userJson(long typeId, String name, String email, String username) {
        return """
                {
                  "name":"%s",
                  "email":"%s",
                  "username":"%s",
                  "password":"Senha123",
                  "street":"Rua A",
                  "number":"10",
                  "city":"Sao Paulo",
                  "state":"SP",
                  "zipCode":"01001-000",
                  "userTypeId":%d
                }
                """.formatted(name, email, username, typeId);
    }

    private String restaurantJson(long ownerId, String name) {
        return """
                {
                  "name":"%s",
                  "address":"Av. Paulista, 1000",
                  "cuisineType":"Italiana",
                  "openingHours":"11:00-23:00",
                  "ownerId":%d
                }
                """.formatted(name, ownerId);
    }

    private String menuItemJson(long restaurantId, String name, String price, boolean dineInOnly) {
        return """
                {
                  "restaurantId":%d,
                  "name":"%s",
                  "description":"Prato artesanal",
                  "price":%s,
                  "dineInOnly":%s,
                  "photoPath":"/images/prato.jpg"
                }
                """.formatted(restaurantId, name, price, dineInOnly);
    }
}
