package com.alurachallenge.forohub.models;


import com.alurachallenge.forohub.dto.NewTopicDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "message"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private String message;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User author;
    private Boolean solved = false;
    @CreationTimestamp
    private LocalDateTime creationDate = LocalDateTime.now();

    public Topic(NewTopicDTO newTopicDTO) {
        this.title = newTopicDTO.title();
        this.message = newTopicDTO.message();
    }
}
