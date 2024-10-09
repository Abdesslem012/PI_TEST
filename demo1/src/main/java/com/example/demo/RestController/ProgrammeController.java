package com.example.demo.RestController;

import com.example.demo.DTO.ProgrammeDTO;
import com.example.demo.Service.ProgrammeService;
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
@RequestMapping(value = "/api/programmes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProgrammeController {

    private final ProgrammeService programmeService;

    public ProgrammeController(final ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    @GetMapping
    public ResponseEntity<List<ProgrammeDTO>> getAllProgrammes() {
        return ResponseEntity.ok(programmeService.findAll());
    }

    @GetMapping("/{programmeId}")
    public ResponseEntity<ProgrammeDTO> getProgramme(
            @PathVariable(name = "programmeId") final Long programmeId) {
        return ResponseEntity.ok(programmeService.get(programmeId));
    }

    @GetMapping("/loadFromJson")
    public String loadProgrammesFromJson() {
        try {
            programmeService.insertProgrammesFromJson();
            return "Programmes loaded from JSON and inserted into database successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load and insert programmes from JSON!";
        }
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProgramme(
            @RequestBody @Valid final ProgrammeDTO programmeDTO) {
        final Long createdProgrammeId = programmeService.create(programmeDTO);
        return new ResponseEntity<>(createdProgrammeId, HttpStatus.CREATED);
    }

    @PutMapping("/{programmeId}")
    public ResponseEntity<Long> updateProgramme(
            @PathVariable(name = "programmeId") final Long programmeId,
            @RequestBody @Valid final ProgrammeDTO programmeDTO) {
        programmeService.update(programmeId, programmeDTO);
        return ResponseEntity.ok(programmeId);
    }

    @DeleteMapping("/{programmeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProgramme(
            @PathVariable(name = "programmeId") final Long programmeId) {
        final ReferencedWarning referencedWarning = programmeService.getReferencedWarning(programmeId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        programmeService.delete(programmeId);
        return ResponseEntity.noContent().build();
    }
}
