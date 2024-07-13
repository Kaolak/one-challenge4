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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid NewCourseDTO courseDTO, UriComponentsBuilder uriComponentsBuilder) {
        try {
            Course newCourse = courseService.createCourse(new Course(courseDTO));
            URI url=uriComponentsBuilder.path("/courses/{id}").buildAndExpand(newCourse.getId()).toUri();
            return ResponseEntity.created(url).body(newCourse);
        }catch (AlreadyExistsException e){
            throw new ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
