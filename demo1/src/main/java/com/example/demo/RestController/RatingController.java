package com.example.demo.RestController;

import com.example.demo.DTO.RatingDTO;
import com.example.demo.Service.RatingService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {
    private final RatingService ratingService;

    public RatingController(final RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        return ResponseEntity.ok(ratingService.findAll());
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> getRating(
            @PathVariable(name = "ratingId") final Long ratingId) {
        return ResponseEntity.ok(ratingService.get(ratingId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRating(@RequestBody @Valid final RatingDTO ratingDTO) {
        final Long createdRatingId = ratingService.create(ratingDTO);
        return new ResponseEntity<>(createdRatingId, HttpStatus.CREATED);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Long> updateRating(@PathVariable(name = "ratingId") final Long ratingId,
                                             @RequestBody @Valid final RatingDTO ratingDTO) {
        ratingService.update(ratingId, ratingDTO);
        return ResponseEntity.ok(ratingId);
    }

    @DeleteMapping("/{ratingId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRating(@PathVariable(name = "ratingId") final Long ratingId) {
        final ReferencedWarning referencedWarning = ratingService.getReferencedWarning(ratingId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ratingService.delete(ratingId);
        return ResponseEntity.noContent().build();
    }

}





