package com.fiap.gestaorestaurante.infrastructure.web;

import com.fiap.gestaorestaurante.application.service.UserService;
import com.fiap.gestaorestaurante.application.service.AuthService;
import com.fiap.gestaorestaurante.application.service.TokenService;
import com.fiap.gestaorestaurante.infrastructure.web.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenService tokenService;

    @Test
    void shouldReturnEmptyJsonArrayWhenSearchHasNoResults() throws Exception {
        when(service.searchByName("ninguém")).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/users/search").param("name", "ninguém"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
