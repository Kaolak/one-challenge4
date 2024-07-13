package com.alurachallenge.forohub.services;

import com.alurachallenge.forohub.dto.NewTopicDTO;
import com.alurachallenge.forohub.models.Course;
import com.alurachallenge.forohub.repositories.CourseRepository;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course updateCourse(Course course) {
        courseRepository.findByIdNotAndName(course.getId(), course.getName()).ifPresent(c -> {
            throw new AlreadyExistsException("Course already exists");
        });
        return courseRepository.save(course);
    }

    public Course createCourse(Course course) {
        courseRepository.findByName(course.getName()).ifPresent(c -> {
            throw new AlreadyExistsException("Course already exists");
        });
        return courseRepository.save(course);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
}
