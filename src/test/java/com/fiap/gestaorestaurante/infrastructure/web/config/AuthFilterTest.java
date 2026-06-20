package com.fiap.gestaorestaurante.infrastructure.web.config;

import com.fiap.gestaorestaurante.application.service.TokenService;
import com.fiap.gestaorestaurante.domain.model.User;
import com.fiap.gestaorestaurante.domain.model.UserType;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

    @Mock
    private TokenService tokenService;

    private AuthFilter filter;

    @BeforeEach
    void setUp() {
        filter = new AuthFilter();
        ReflectionTestUtils.setField(filter, "tokenService", tokenService);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSkipPublicRoutes() {
        assertThat(filter.shouldNotFilter(request("/api/v1/login"))).isTrue();
        assertThat(filter.shouldNotFilter(request("/api/v1/users/login"))).isTrue();
        assertThat(filter.shouldNotFilter(request("/v3/api-docs"))).isTrue();
        assertThat(filter.shouldNotFilter(request("/swagger-ui/index.html"))).isTrue();
        assertThat(filter.shouldNotFilter(request("/api/v1/restaurants"))).isFalse();
    }

    @Test
    void shouldContinueWithoutAuthorizationHeader() throws ServletException, IOException {
        MockHttpServletResponse response = doFilter(request("/api/v1/restaurants"));

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(tokenService, never()).getUsuarioFromToken(org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void shouldRejectAuthorizationHeaderWithoutBearerPrefix() throws ServletException, IOException {
        MockHttpServletRequest request = request("/api/v1/restaurants");
        request.addHeader("Authorization", "Basic abc");

        MockHttpServletResponse response = doFilter(request);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getContentAsString()).contains("Header deve iniciar com Bearer");
    }

    @Test
    void shouldRejectInvalidToken() throws ServletException, IOException {
        MockHttpServletRequest request = request("/api/v1/restaurants");
        request.addHeader("Authorization", "Bearer invalido");
        when(tokenService.getUsuarioFromToken("invalido")).thenReturn(null);

        MockHttpServletResponse response = doFilter(request);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getContentAsString()).contains("Token inválido");
    }

    @Test
    void shouldAuthenticateValidToken() throws ServletException, IOException {
        MockHttpServletRequest request = request("/api/v1/restaurants");
        request.addHeader("Authorization", "Bearer valido");
        User user = new User(
                "Maria Silva",
                "maria@email.com",
                "maria",
                "senha",
                "Rua A",
                "10",
                "Sao Paulo",
                "SP",
                "01000-000",
                new UserType("ADMIN")
        );
        when(tokenService.getUsuarioFromToken("valido")).thenReturn(user);

        MockHttpServletResponse response = doFilter(request);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isSameAs(user);
    }

    private MockHttpServletResponse doFilter(MockHttpServletRequest request) throws ServletException, IOException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilterInternal(request, response, new MockFilterChain());
        return response;
    }

    private MockHttpServletRequest request(String path) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", path);
        request.setRequestURI(path);
        return request;
    }
}
