package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.NewTopicDTO;
import com.alurachallenge.forohub.dto.TopicDTO;
import com.alurachallenge.forohub.dto.UpdateTopicDTO;
import com.alurachallenge.forohub.models.Course;
import com.alurachallenge.forohub.models.Topic;
import com.alurachallenge.forohub.models.User;
import com.alurachallenge.forohub.services.CourseService;
import com.alurachallenge.forohub.services.TopicService;
import com.alurachallenge.forohub.services.UserService;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final UserService userService;
    private final CourseService courseService;

    public TopicController(TopicService topicService, UserService userService, CourseService courseService) {
        this.topicService = topicService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getTopics() {
        return ResponseEntity.ok(topicService.findAll()
                .stream()
                .map(TopicDTO::new)
                .toList()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable Long id) {
        Topic topic = topicService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        return ResponseEntity.ok(new TopicDTO(topic));
    }

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody @Valid NewTopicDTO newTopicDTO, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal String username) {
        User author = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Course course = courseService.findById(newTopicDTO.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        Topic topic = new Topic(newTopicDTO);
        topic.setAuthor(author);
        topic.setCourse(course);
        try {
            Topic newTopic=topicService.createTopic(topic);
            URI url=uriComponentsBuilder.path("/topics/{id}").buildAndExpand(newTopic.getId()).toUri();
        return ResponseEntity.created(url).body(new TopicDTO(newTopic));

        }catch (AlreadyExistsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id, @RequestBody @Valid UpdateTopicDTO updatedTopicDTO, @AuthenticationPrincipal String username) {

        User author = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicService.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        if (!topic.getAuthor().equals(author)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this topic");
        }
        topic.setTitle(updatedTopicDTO.title());
        topic.setMessage(updatedTopicDTO.message());
        try {
            return ResponseEntity.ok(new TopicDTO(topicService.updateTopic(topic)));
        } catch (AlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id, @AuthenticationPrincipal String username) {
        User author = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        if (!topic.getAuthor().equals(author)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this topic");
        }
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/solve")
    public ResponseEntity<TopicDTO> toggleSolved(@PathVariable Long id, @AuthenticationPrincipal String username){
        User author = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        if (!topic.getAuthor().equals(author)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this topic");
        }
        topic.setSolved(!topic.getSolved());
        return ResponseEntity.ok(new TopicDTO(topicService.updateTopic(topic)));
    }

}
