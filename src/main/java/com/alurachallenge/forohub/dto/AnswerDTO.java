package com.alurachallenge.forohub.dto;

import java.time.LocalDateTime;
import com.alurachallenge.forohub.models.Answer;

public record AnswerDTO(
        Long id,
        String message,
        UserDTO author,
        TopicDTO topic,
        Boolean solution,
        LocalDateTime creationDate) {
    public AnswerDTO (Answer answer){
        this(answer.getId(), answer.getMessage(), new UserDTO(answer.getAuthor()), new TopicDTO(answer.getTopic()), answer.getSolution(), answer.getCreationDate());
    }
}
