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

    /*@PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUnit(@RequestBody @Valid final UnitDTO unitDTO) {
        final Long createdUnitId = unitService.create(unitDTO);
        return new ResponseEntity<>(createdUnitId, HttpStatus.CREATED);
    }*/

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUnit(@RequestParam("content") MultipartFile contentFile,
                                           @RequestParam("name") String name,
                                           @RequestParam("description") String description) {
        try {
            String contentUrl = saveFileToServer(contentFile);

            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setName(name);
            unitDTO.setDescription(description);
            unitDTO.setContent(contentUrl);

            final Long createdUnitId = unitService.create(unitDTO);
            return new ResponseEntity<>(createdUnitId, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String saveFileToServer(MultipartFile contentFile) throws IOException {
        String uploadDir = "src\\main\\resources\\static\\pdf"; // Pour obtenir le chemin absolu

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + contentFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.copy(contentFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/pdf/" + fileName; // Assurez-vous que ce chemin est correctement servi par Spring Boot
    }


    /*@PutMapping("/{unitId}")
    public ResponseEntity<Long> updateUnit(@PathVariable(name = "unitId") final Long unitId,
            @RequestBody @Valid final UnitDTO unitDTO) {
        unitService.update(unitId, unitDTO);
        return ResponseEntity.ok(unitId);
    }*/


    private static final String PDF_DIRECTORY = "src\\main\\resources\\static\\pdf"; // Chemin d'accès au répertoire des fichiers PDF

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

    @PutMapping(path = "/{unitId}" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> updateUnit(@PathVariable(name = "unitId") Long unitId,
                                           @RequestParam(value = "content") MultipartFile contentFile,
                                           @RequestParam("name") String name,
                                           @RequestParam("description") String description) {
        try {
            // Si un nouveau fichier image est fourni, enregistrez-le sur le serveur et récupérez son URL
            String contentUrl = null;
            if (contentFile != null && !contentFile.isEmpty()) {
                contentUrl = saveFileToServer(contentFile);
            }

            // Créer une instance de CoursDTO et lui attribuer les données fournies
            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setName(name);
            unitDTO.setDescription(description);

            // Si une nouvelle image a été fournie, mettre à jour l'URL de l'image
            if (contentUrl != null) {
                unitDTO.setContent(contentUrl);
            }

            // Mettre à jour le cours avec le DTO fourni
            unitService.update(unitId, unitDTO);

            return ResponseEntity.ok(unitId);
        } catch (IOException e) {
            // Gérer les erreurs de lecture du fichier
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            // Gérer d'autres exceptions non prévues
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
