package com.example.demo.RestController;

import com.example.demo.DTO.ForumDTO;
import com.example.demo.Service.ForumService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/forums", produces = MediaType.APPLICATION_JSON_VALUE)
public class ForumController {
    private final ForumService forumService;

    public ForumController(final ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ForumDTO>> getAllForums() {
        return ResponseEntity.ok(forumService.findAll());
    }

    @GetMapping("/{forumId}")
    public ResponseEntity<ForumDTO> getForum(@PathVariable(name = "forumId") final Long forumId) {
        return ResponseEntity.ok(forumService.get(forumId));
    }

    @PostMapping
    public ResponseEntity<Long> createForum(@RequestBody @Valid final ForumDTO forumDTO) {
        final Long createdForumId = forumService.create(forumDTO);
        return new ResponseEntity<>(createdForumId, HttpStatus.CREATED);
    }

    @PutMapping("/{forumId}")
    public ResponseEntity<Long> updateForum(@PathVariable(name = "forumId") final Long forumId,
                                            @RequestBody @Valid final ForumDTO forumDTO) {
        forumService.update(forumId, forumDTO);
        return ResponseEntity.ok(forumId);
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<Void> deleteForum(@PathVariable(name = "forumId") final Long forumId) {
        final ReferencedWarning referencedWarning = forumService.getReferencedWarning(forumId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        forumService.delete(forumId);
        return ResponseEntity.noContent().build();
    }

}

