package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTopicDTO(
        @NotBlank
        String title,
        @NotBlank
        String message) {
}
