package com.example.demo.RestController;

import com.example.demo.DTO.PersonalizedAdviceDTO;
import com.example.demo.Service.PersonalizedAdviceService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/personalizedAdvices", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonalizedAdviceResource {

    private final PersonalizedAdviceService personalizedAdviceService;


    public PersonalizedAdviceResource(final PersonalizedAdviceService personalizedAdviceService) {
        this.personalizedAdviceService = personalizedAdviceService;

    }

    @GetMapping
    public ResponseEntity<List<PersonalizedAdviceDTO>> getAllPersonalizedAdvices() {
        return ResponseEntity.ok(personalizedAdviceService.findAll());
    }

    @GetMapping("/{idPersonalized}")
    public ResponseEntity<PersonalizedAdviceDTO> getPersonalizedAdvice(
            @PathVariable(name = "idPersonalized") final Long idPersonalized) {
        return ResponseEntity.ok(personalizedAdviceService.get(idPersonalized));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPersonalizedAdvice(
            @RequestBody @Valid final PersonalizedAdviceDTO personalizedAdviceDTO) {
        final Long createdIdPersonalized = personalizedAdviceService.create(personalizedAdviceDTO);
        return new ResponseEntity<>(createdIdPersonalized, HttpStatus.CREATED);
    }

    @PutMapping("/{idPersonalized}")
    public ResponseEntity<Long> updatePersonalizedAdvice(
            @PathVariable(name = "idPersonalized") final Long idPersonalized,
            @RequestBody @Valid final PersonalizedAdviceDTO personalizedAdviceDTO) {
        personalizedAdviceService.update(idPersonalized, personalizedAdviceDTO);
        return ResponseEntity.ok(idPersonalized);
    }

    @DeleteMapping("/{idPersonalized}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePersonalizedAdvice(
            @PathVariable(name = "idPersonalized") final Long idPersonalized) {
        personalizedAdviceService.delete(idPersonalized);
        return ResponseEntity.noContent().build();
    }



}
