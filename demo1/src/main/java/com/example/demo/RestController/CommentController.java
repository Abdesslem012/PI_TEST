package com.example.demo.RestController;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.Entity.Comment;
import com.example.demo.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getCommentsByPostId/{postId}")
    public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.findCommentsByPostId(postId);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(commentService.get(id));
    }

    @PostMapping("/add-comment")
    public ResponseEntity<Comment> addCommentToPost(
            @RequestParam(name = "postId") Long postId,
            @RequestBody Comment comment
    ) {
        // Use the service method to add a comment to the specified post
        Comment createdComment = commentService.addCommentToPost(postId, comment);

        // Return the created comment and set the status to HTTP 201 Created
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/update-comment/{id}")
    public ResponseEntity<Long> updateComment(@PathVariable(name = "id") final Long id,
                                              @RequestBody @Valid final CommentDTO commentDTO) {
        commentService.update(id, commentDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") final Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    /*@PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addCommentToPost(
            @PathVariable Long postId,
            @RequestBody Comment comment) {

        Comment createdComment = commentService.addCommentToPost(postId, comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }*/

}

