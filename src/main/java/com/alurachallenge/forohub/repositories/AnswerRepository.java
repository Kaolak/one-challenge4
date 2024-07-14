package com.alurachallenge.forohub.repositories;
import com.alurachallenge.forohub.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {


    void deleteByTopicId(Long id);
}
