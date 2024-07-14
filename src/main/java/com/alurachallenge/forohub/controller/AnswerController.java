package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.AnswerDTO;
import com.alurachallenge.forohub.dto.NewAnswerDTO;
import com.alurachallenge.forohub.models.Topic;
import com.alurachallenge.forohub.models.User;
import com.alurachallenge.forohub.services.AnswerService;
import com.alurachallenge.forohub.services.TopicService;
import com.alurachallenge.forohub.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.alurachallenge.forohub.models.Answer;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private  final AnswerService answerService;
    private final UserService userService;
    private final TopicService topicService;

    public AnswerController(AnswerService answerService, UserService userService, TopicService topicService) {
        this.answerService = answerService;
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<AnswerDTO>> getAnswers() {
        return ResponseEntity.ok(answerService.findAll()
                .stream()
                .map(AnswerDTO::new)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long id) {
        Answer answer = answerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer not found"));
        return ResponseEntity.ok(new AnswerDTO(answer));
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> createAnswer(@RequestBody @Valid NewAnswerDTO newAnswerDTO, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal String username) {
        User author = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicService.findById(newAnswerDTO.topicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        Answer answer = new Answer();
        answer.setMessage(newAnswerDTO.message());
        answer.setAuthor(author);
        answer.setTopic(topic);
        return ResponseEntity.created(uriComponentsBuilder.path("/answers/{id}").buildAndExpand(answerService.createAnswer(answer).getId()).toUri()).body(new AnswerDTO(answer));
    }
}
