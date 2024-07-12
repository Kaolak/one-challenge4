package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.NewCourseDTO;
import com.alurachallenge.forohub.models.Course;
import com.alurachallenge.forohub.services.CourseService;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid NewCourseDTO courseDTO) {
        try {
            return ResponseEntity.ok(courseService.createCourse(new Course(courseDTO)));
        }catch (AlreadyExistsException e){
            throw new ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
