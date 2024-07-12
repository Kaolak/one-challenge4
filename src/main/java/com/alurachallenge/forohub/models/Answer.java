package com.alurachallenge.forohub.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Topic topic;
        private String message;
        private Boolean solution = false;
        @ManyToOne
        private User author;
        @CreationTimestamp
        private LocalDateTime creationDate = LocalDateTime.now();
    }