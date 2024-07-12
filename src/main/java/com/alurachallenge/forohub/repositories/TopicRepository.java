package com.alurachallenge.forohub.repositories;

import com.alurachallenge.forohub.models.Course;
import com.alurachallenge.forohub.models.Topic;
import com.alurachallenge.forohub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByTitleAndMessage(String title, String message);
    Optional<Topic> findByIdNotAndTitleAndMessage(Long id, String title, String message);

    List<Topic> findByCourse(Course courseName);

    List<Topic> findByAuthorId(Long authorId);
    List<Topic> findByAuthor(User author);
}
