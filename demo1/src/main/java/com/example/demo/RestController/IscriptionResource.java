package com.example.demo.RestController;


import com.example.demo.DTO.IscriptionDTO;
import com.example.demo.Service.IscriptionService;
import com.example.demo.Service.NotificationService;
import com.example.demo.util.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/iscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class IscriptionResource {

    private final IscriptionService iscriptionService;
    private  final NotificationService notificationService;

    public IscriptionResource(final IscriptionService iscriptionService, NotificationService notificationService) {
        this.iscriptionService = iscriptionService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<IscriptionDTO>> getAllIscriptions() {
        return ResponseEntity.ok(iscriptionService.findAll());
    }

    @GetMapping("/{idInscription}")
    public ResponseEntity<IscriptionDTO> getIscription(
            @PathVariable(name = "idInscription") final Long idInscription) {
        return ResponseEntity.ok(iscriptionService.get(idInscription));
    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createIscription(
            @RequestBody @Valid final IscriptionDTO iscriptionDTO) {
        final Long createdIdInscription = iscriptionService.create(iscriptionDTO);
        // Envoyer la notification à l'administrateur
        notificationService.sendNotificationToAdmin(iscriptionDTO.getEventId(), iscriptionDTO.getStudentId(), createdIdInscription);
        return new ResponseEntity<>(createdIdInscription, HttpStatus.CREATED);
    }

    @PutMapping("/{idInscription}")
    public ResponseEntity<Long> updateIscription(
            @PathVariable(name = "idInscription") final Long idInscription,
            @RequestBody @Valid final IscriptionDTO iscriptionDTO) {
        iscriptionService.update(idInscription, iscriptionDTO);
        return ResponseEntity.ok(idInscription);
    }

    @DeleteMapping("/{idInscription}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteIscription(
            @PathVariable(name = "idInscription") final Long idInscription) {
        iscriptionService.delete(idInscription);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/api/inscription/details")
    public ResponseEntity<IscriptionDTO> getInscriptionDetails(@RequestParam Long idInscription) {
        try {
            IscriptionDTO inscriptionDTO = iscriptionService.getInscriptionDetails(idInscription);
            return ResponseEntity.ok(inscriptionDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idInscription}/accept")
    public ResponseEntity<String> acceptInscriptionById(@PathVariable Long idInscription) {
        boolean acceptationReussie = iscriptionService.accepterInscription(idInscription);
        if (acceptationReussie) {
            return ResponseEntity.ok("L'inscription a été acceptée avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'inscription n'a pas été trouvée.");
        }
    }


    @GetMapping("/total")
    public Long getTotalInscriptions() {
        return iscriptionService.getTotalInscriptions();
    }

}
