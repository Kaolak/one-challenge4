package com.alurachallenge.forohub.dto;

import com.alurachallenge.forohub.models.Course;
import com.alurachallenge.forohub.models.Topic;

public record TopicDTO(
        Long id,
        String title,
        String message,
        Course course,
        UserDTO author) {
    public TopicDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCourse(), new UserDTO(topic.getAuthor()));
    }
}
