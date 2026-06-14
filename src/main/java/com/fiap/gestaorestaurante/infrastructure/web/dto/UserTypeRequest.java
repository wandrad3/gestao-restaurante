package com.fiap.gestaorestaurante.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserTypeRequest(
        @NotBlank @Size(max = 80) String name
) {
}
