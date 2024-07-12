package com.alurachallenge.forohub.models;


import com.alurachallenge.forohub.dto.NewCourseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String category;

    public Course(NewCourseDTO courseDTO) {
        this.name = courseDTO.name();
        this.category = courseDTO.category();
    }
}
