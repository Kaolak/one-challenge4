package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record NewRoleDTO(
        @NotBlank
        String name
) {
}
