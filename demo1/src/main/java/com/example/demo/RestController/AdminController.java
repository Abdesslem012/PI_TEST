package com.example.demo.RestController;


import com.example.demo.Repository.IscriptionRepository;
import com.example.demo.Service.IscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IscriptionService iscriptionService;

    @Autowired
    private IscriptionRepository iscriptionRepository;

    @PostMapping("/accepter-inscription")
    public ResponseEntity<?> accepterInscription(@RequestParam Long idInscription) {
        iscriptionService.accepterInscription(idInscription);
        return ResponseEntity.ok("Inscription acceptée avec succès pour ID=" + idInscription);
    }











}
