package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import com.alurachallenge.forohub.models.Answer;
import jakarta.validation.constraints.NotNull;

public record NewAnswerDTO(
        @NotNull
        Long topicId,
        @NotBlank
        String message) {
}
