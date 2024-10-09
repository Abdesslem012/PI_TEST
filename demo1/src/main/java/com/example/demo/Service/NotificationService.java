package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private IscriptionService iscriptionService;
    public void sendNotification(String recipient, String subject, String message) {
        // Logique pour envoyer une notification à un destinataire donné
        System.out.println("Sending notification to " + recipient + " - Subject: " + subject + " - Message: " + message);
        // Ici, vous pouvez implémenter le code pour envoyer un email, un SMS, une notification push, etc.
    }

    public void sendNotificationToAdmin(String eventId, String studentId, Long idInscription) {


        // Construire le message de notification
        String recipientEmail = "ccourzelo@gmail.com"; // Remplacez par l'email de l'administrateur
        String subject = "Nouvelle inscription à un événement";
        String acceptUrl = "http://localhost:8089/api/iscriptions/" + idInscription + "/accept";
        String message = "L'étudiant " + studentId + " s'est inscrit à l'événement " + eventId +
                ". Cliquez sur le bouton ci-dessous pour accepter l'inscription : <br>" +
                "<a href=\"" + acceptUrl + "\">Accepter l'inscription</a>";

        // Envoyer l'e-mail de notification à l'administrateur
        sendEmail(recipientEmail, subject, message);
    }





    private void sendEmail(String recipient, String subject, String message) {
        // Logique pour envoyer l'email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage); // Envoyer l'email
    }

    public void sendConfirmationEmail(String recipient, String subject, String message) {
        // Logique pour envoyer l'e-mail de confirmation à l'étudiant
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage); // Envoyer l'e-mail de confirmation
    }



}
