/*package com.example.demo.RestController;

import com.example.demo.DTO.ObjectivesDTO;

import com.example.demo.Service.ObjectivesService;
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
@RequestMapping(value = "/api/objectivess", produces = MediaType.APPLICATION_JSON_VALUE)
public class ObjectivesController {
    private final ObjectivesService objectivesService;

    public ObjectivesController(final ObjectivesService objectivesService) {
        this.objectivesService = objectivesService;
    }

    @GetMapping
    public ResponseEntity<List<ObjectivesDTO>> getAllObjectivess() {
        return ResponseEntity.ok(objectivesService.findAll());
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/{objectivesId}")
    public ResponseEntity<ObjectivesDTO> getObjectives(
            @PathVariable(name = "objectivesId") final Long objectivesId) {
        return ResponseEntity.ok(objectivesService.get(objectivesId));
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createObjectives(
            @RequestBody @Valid final ObjectivesDTO objectivesDTO) {
        final Long createdObjectivesId = objectivesService.create(objectivesDTO);
        return new ResponseEntity<>(createdObjectivesId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PutMapping("/{objectivesId}")
    public ResponseEntity<Long> updateObjectives(
            @PathVariable(name = "objectivesId") final Long objectivesId,
            @RequestBody @Valid final ObjectivesDTO objectivesDTO) {
        objectivesService.update(objectivesId, objectivesDTO);
        return ResponseEntity.ok(objectivesId);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @DeleteMapping("/{objectivesId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteObjectives(
            @PathVariable(name = "objectivesId") final Long objectivesId) {
        objectivesService.delete(objectivesId);
        return ResponseEntity.noContent().build();
    }

}
*/