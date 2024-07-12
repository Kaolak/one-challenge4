package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record NewCourseDTO (
        @NotBlank
        String name,
        @NotBlank
        String category
) {
}
