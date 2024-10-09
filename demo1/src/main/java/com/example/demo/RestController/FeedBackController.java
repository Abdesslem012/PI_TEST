package com.example.demo.RestController;

import com.example.demo.DTO.FeedBackDTO;
import com.example.demo.Service.FeedBackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/feedBacks", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedBackController {
    private final FeedBackService feedBackService;

    public FeedBackController(final FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @GetMapping
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBacks() {
        return ResponseEntity.ok(feedBackService.findAll());
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedBackDTO> getFeedBack(
            @PathVariable(name = "feedbackId") final Long feedbackId) {
        return ResponseEntity.ok(feedBackService.get(feedbackId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFeedBack(@RequestBody @Valid final FeedBackDTO feedBackDTO) {
        final Long createdFeedbackId = feedBackService.create(feedBackDTO);
        return new ResponseEntity<>(createdFeedbackId, HttpStatus.CREATED);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Long> updateFeedBack(
            @PathVariable(name = "feedbackId") final Long feedbackId,
            @RequestBody @Valid final FeedBackDTO feedBackDTO) {
        feedBackService.update(feedbackId, feedBackDTO);
        return ResponseEntity.ok(feedbackId);
    }

    @DeleteMapping("/{feedbackId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFeedBack(
            @PathVariable(name = "feedbackId") final Long feedbackId) {
        feedBackService.delete(feedbackId);
        return ResponseEntity.noContent().build();
    }

}

