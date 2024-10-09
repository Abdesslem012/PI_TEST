/*package com.example.demo.RestController;

import com.example.demo.DTO.MoitoringAcadimicObjectivesDTO;
import com.example.demo.Service.MoitoringAcadimicObjectivesService;
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
@RequestMapping(value = "/api/moitoringAcadimicObjectivess", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoitoringAcadimicObjectivesController {
    private final MoitoringAcadimicObjectivesService moitoringAcadimicObjectivesService;

    public MoitoringAcadimicObjectivesController(
            final MoitoringAcadimicObjectivesService moitoringAcadimicObjectivesService) {
        this.moitoringAcadimicObjectivesService = moitoringAcadimicObjectivesService;
    }

    @GetMapping
    public ResponseEntity<List<MoitoringAcadimicObjectivesDTO>> getAllMoitoringAcadimicObjectivess(
    ) {
        return ResponseEntity.ok(moitoringAcadimicObjectivesService.findAll());
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/{maoId}")
    public ResponseEntity<MoitoringAcadimicObjectivesDTO> getMoitoringAcadimicObjectives(
            @PathVariable(name = "maoId") final Long maoId) {
        return ResponseEntity.ok(moitoringAcadimicObjectivesService.get(maoId));
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMoitoringAcadimicObjectives(
            @RequestBody @Valid final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        final Long createdMaoId = moitoringAcadimicObjectivesService.create(moitoringAcadimicObjectivesDTO);
        return new ResponseEntity<>(createdMaoId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PutMapping("/{maoId}")
    public ResponseEntity<Long> updateMoitoringAcadimicObjectives(
            @PathVariable(name = "maoId") final Long maoId,
            @RequestBody @Valid final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        moitoringAcadimicObjectivesService.update(maoId, moitoringAcadimicObjectivesDTO);
        return ResponseEntity.ok(maoId);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @DeleteMapping("/{maoId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMoitoringAcadimicObjectives(
            @PathVariable(name = "maoId") final Long maoId) {
        final ReferencedWarning referencedWarning = moitoringAcadimicObjectivesService.getReferencedWarning(maoId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        moitoringAcadimicObjectivesService.delete(maoId);
        return ResponseEntity.noContent().build();
    }

}

*/