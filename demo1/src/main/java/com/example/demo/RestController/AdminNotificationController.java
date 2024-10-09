 package com.example.demo.RestController;


import com.example.demo.DTO.NotificationData;
import com.example.demo.Service.IscriptionService;
import com.example.demo.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminNotificationController {

    @Autowired
    private NotificationService notificationService; // Injectez votre service de notification

    @Autowired
    private IscriptionService iscriptionService;

    @PostMapping("/notification")
    public void sendNotificationToAdmin(@RequestBody NotificationData notificationData) {
        // Logique pour envoyer la notification à l'administrateur
        notificationService.sendNotificationToAdmin(notificationData.getEventId(), notificationData.getStudentId(), notificationData.getIdInscription());
    }






}