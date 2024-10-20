package com.example.demo.RestController;

import com.example.demo.DTO.UnitDTO;
import com.example.demo.Service.UnitService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/units", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnitController {

    private final UnitService unitService;

    // Define constants for repeated strings
    private static final String UPLOAD_DIR = "src/main/resources/static/pdf";
    private static final String PDF_DIRECTORY = UPLOAD_DIR;
    private static final String CONTENT_PARAM = "content";
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String FILE_NAME_PREFIX = "/pdf/";

    public UnitController(final UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllUnits() {
        return ResponseEntity.ok(unitService.findAll());
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<UnitDTO> getUnit(@PathVariable(name = "unitId") final Long unitId) {
        return ResponseEntity.ok(unitService.get(unitId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUnit(@RequestParam(CONTENT_PARAM) MultipartFile contentFile,
                                           @RequestParam(NAME_PARAM) String name,
                                           @RequestParam(DESCRIPTION_PARAM) String description) {
        try {
            String contentUrl = saveFileToServer(contentFile);

            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setName(name);
            unitDTO.setDescription(description);
            unitDTO.setContent(contentUrl);

            final Long createdUnitId = unitService.create(unitDTO);
            return new ResponseEntity<>(createdUnitId, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String saveFileToServer(MultipartFile contentFile) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + contentFile.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        Files.copy(contentFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return FILE_NAME_PREFIX + fileName; // Ensure this path is correctly served by Spring Boot
    }

    @GetMapping("/pdf/{filename}")
    public ResponseEntity<InputStreamResource> getPdf(@PathVariable String filename) throws IOException {
        Path pdfPath = Paths.get(PDF_DIRECTORY, filename);
        File pdfFile = pdfPath.toFile();

        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new FileInputStream(pdfFile)));
    }

    @PutMapping(path = "/{unitId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> updateUnit(@PathVariable(name = "unitId") Long unitId,
                                           @RequestParam(value = CONTENT_PARAM) MultipartFile contentFile,
                                           @RequestParam(NAME_PARAM) String name,
                                           @RequestParam(DESCRIPTION_PARAM) String description) {
        try {
            // If a new content file is provided, save it on the server and get its URL
            String contentUrl = null;
            if (contentFile != null && !contentFile.isEmpty()) {
                contentUrl = saveFileToServer(contentFile);
            }

            // Create a UnitDTO instance and assign the provided data
            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setName(name);
            unitDTO.setDescription(description);

            // If a new content file was provided, update the content URL
            if (contentUrl != null) {
                unitDTO.setContent(contentUrl);
            }

            // Update the unit with the provided DTO
            unitService.update(unitId, unitDTO);

            return ResponseEntity.ok(unitId);
        } catch (Exception e) {
            // Handle file reading errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{unitId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUnit(@PathVariable(name = "unitId") final Long unitId) {
        final ReferencedWarning referencedWarning = unitService.getReferencedWarning(unitId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        unitService.delete(unitId);
        return ResponseEntity.noContent().build();
    }
}
