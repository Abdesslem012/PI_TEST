package com.example.demo.RestController;

import com.example.demo.DTO.SpecializationDTO;
import com.example.demo.Service.SpecializationService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/specializations", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpecializationController {
    private final SpecializationService specializationService;

    public SpecializationController(final SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping
    public ResponseEntity<List<SpecializationDTO>> getAllSpecializations() {
        return ResponseEntity.ok(specializationService.findAll());
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<SpecializationDTO> getSpecialization(
            @PathVariable(name = "specializationId") final Long specializationId) {
        return ResponseEntity.ok(specializationService.get(specializationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSpecialization(
            @RequestBody @Valid final SpecializationDTO specializationDTO) {
        final Long createdSpecializationId = specializationService.create(specializationDTO);
        return new ResponseEntity<>(createdSpecializationId, HttpStatus.CREATED);
    }

    @GetMapping("/loadFromJson")
    public String loadSpecializationsFromJson() {
        try {
            specializationService.insertSpecializationsFromJson();
            return "Specializations loaded from JSON and inserted into database successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load and insert specializations from JSON!";
        }
    }

    @PutMapping("/{specializationId}")
    public ResponseEntity<Long> updateSpecialization(
            @PathVariable(name = "specializationId") final Long specializationId,
            @RequestBody @Valid final SpecializationDTO specializationDTO) {
        specializationService.update(specializationId, specializationDTO);
        return ResponseEntity.ok(specializationId);
    }

    @DeleteMapping("/{specializationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSpecialization(
            @PathVariable(name = "specializationId") final Long specializationId) {
        final ReferencedWarning referencedWarning = specializationService.getReferencedWarning(specializationId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        specializationService.delete(specializationId);
        return ResponseEntity.noContent().build();
    }

}
