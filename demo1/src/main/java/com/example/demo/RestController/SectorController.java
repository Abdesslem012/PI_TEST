package com.example.demo.RestController;

import com.example.demo.DTO.SectorDTO;
import com.example.demo.Service.SectorService;
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
@RequestMapping(value = "/api/sectors", produces = MediaType.APPLICATION_JSON_VALUE)
public class SectorController {
    private final SectorService sectorService;
    private final SpecializationService specializationService;

    public SectorController(final SectorService sectorService, SpecializationService specializationService) {
        this.sectorService = sectorService;
        this.specializationService = specializationService;
    }

    @GetMapping
    public ResponseEntity<List<SectorDTO>> getAllSectors() {
        return ResponseEntity.ok(sectorService.findAll());
    }


    @GetMapping("/loadFromJson")
    public String loadSectorsFromJson() {
        try {
            sectorService.insertSectorsFromJson();
            return "Sectors loaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load sectors";
        }
    }


    @GetMapping("/{sectorId}")
    public ResponseEntity<SectorDTO> getSector(
            @PathVariable(name = "sectorId") final Long sectorId) {
        return ResponseEntity.ok(sectorService.get(sectorId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSector(@RequestBody @Valid final SectorDTO sectorDTO) {
        final Long createdSectorId = sectorService.create(sectorDTO);
        return new ResponseEntity<>(createdSectorId, HttpStatus.CREATED);
    }

    @PutMapping("/{sectorId}")
    public ResponseEntity<Long> updateSector(@PathVariable(name = "sectorId") final Long sectorId,
                                             @RequestBody @Valid final SectorDTO sectorDTO) {
        sectorService.update(sectorId, sectorDTO);
        return ResponseEntity.ok(sectorId);
    }

    @DeleteMapping("/{sectorId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSector(@PathVariable(name = "sectorId") final Long sectorId) {
        final ReferencedWarning referencedWarning = sectorService.getReferencedWarning(sectorId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sectorService.delete(sectorId);
        return ResponseEntity.noContent().build();
    }

}
