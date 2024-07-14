package com.alurachallenge.forohub.services;

import com.alurachallenge.forohub.models.Topic;
import com.alurachallenge.forohub.repositories.AnswerRepository;
import com.alurachallenge.forohub.repositories.TopicRepository;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;

    public TopicService(TopicRepository topicRepository, AnswerRepository answerRepository) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
    }

    public Topic updateTopic(Topic topic) {
        topicRepository.findByIdNotAndTitleAndMessage(topic.getId(), topic.getTitle(), topic.getMessage()).ifPresent(t -> {
            throw new AlreadyExistsException("Topic already exists");
        });

        return topicRepository.save(topic);
    }

    public Topic createTopic(Topic topic) {
        topicRepository.findByTitleAndMessage(topic.getTitle(), topic.getMessage()).ifPresent(t -> {
            throw new AlreadyExistsException("Topic already exists");
        });

        return topicRepository.save(topic);
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Transactional
    public void deleteTopic(Long id) {
        answerRepository.deleteByTopicId(id);
        topicRepository.deleteById(id);
    }
}
