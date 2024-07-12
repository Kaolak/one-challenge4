package com.alurachallenge.forohub.repositories;

import com.alurachallenge.forohub.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    List<Course> findByCategory(String categoryName);

    Optional<Course> findByIdNotAndName(Long id, String name);

}
