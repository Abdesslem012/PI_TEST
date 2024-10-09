package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendReminderEmail(String recipientEmail, String subject, String content) {
        // Code pour envoyer l'e-mail de rappel au destinataire
        // Vous devez implémenter la logique d'envoi d'e-mail ici
        System.out.println("Sending reminder email to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
        // Envoyer réellement l'e-mail ici (vous pouvez utiliser JavaMailSender ou un service tiers)
    }

    public void sendAssignmentNotification(String recipientEmail, String className) {
        String subject = "Assignment Notification";
        String content = "You have been assigned to class " + className + ".";
        sendSimpleEmail(recipientEmail, subject, content);
    }
}
