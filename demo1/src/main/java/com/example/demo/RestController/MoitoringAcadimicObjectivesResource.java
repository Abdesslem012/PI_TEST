package com.example.demo.RestController;



import com.example.demo.DTO.MoitoringAcadimicObjectivesDTO;
import com.example.demo.Service.MoitoringAcadimicObjectivesService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
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
@RequestMapping(value = "/api/moitoringAcadimicObjectivess", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoitoringAcadimicObjectivesResource {

    private final MoitoringAcadimicObjectivesService moitoringAcadimicObjectivesService;

    public MoitoringAcadimicObjectivesResource(
            final MoitoringAcadimicObjectivesService moitoringAcadimicObjectivesService) {
        this.moitoringAcadimicObjectivesService = moitoringAcadimicObjectivesService;
    }

    @GetMapping
    public ResponseEntity<List<MoitoringAcadimicObjectivesDTO>> getAllMoitoringAcadimicObjectivess(
            ) {
        return ResponseEntity.ok(moitoringAcadimicObjectivesService.findAll());
    }

    @GetMapping("/{maoId}")
    public ResponseEntity<MoitoringAcadimicObjectivesDTO> getMoitoringAcadimicObjectives(
            @PathVariable(name = "maoId") final Long maoId) {
        return ResponseEntity.ok(moitoringAcadimicObjectivesService.get(maoId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMoitoringAcadimicObjectives(
            @RequestBody @Valid final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        final Long createdMaoId = moitoringAcadimicObjectivesService.create(moitoringAcadimicObjectivesDTO);
        return new ResponseEntity<>(createdMaoId, HttpStatus.CREATED);
    }


    @PutMapping("/{maoId}")
    public ResponseEntity<Long> updateMoitoringAcadimicObjectives(
            @PathVariable(name = "maoId") final Long maoId,
            @RequestBody @Valid final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        moitoringAcadimicObjectivesService.update(maoId, moitoringAcadimicObjectivesDTO);
        return ResponseEntity.ok('"' + maoId + '"');
    }

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

    @GetMapping("/generate-advices")
    public String generatePersonalizedAdvices() {
        moitoringAcadimicObjectivesService.generatePersonalizedAdvices();
        return "Conseils personnalisés générés avec succès.";
    }

    @GetMapping("/academic-performance")
    public ResponseEntity<MoitoringAcadimicObjectivesDTO> getAcademicPerformanceData() {
        MoitoringAcadimicObjectivesDTO performanceDTO = moitoringAcadimicObjectivesService.calculateAcademicPerformance();
        return ResponseEntity.ok(performanceDTO);
    }

}
