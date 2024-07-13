package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewTopicDTO(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        Long courseId
) {
}
