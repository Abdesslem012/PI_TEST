package com.example.demo.RestController;

import com.example.demo.DTO.EmailRequest;
import com.example.demo.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        return ResponseEntity.ok("Email sent successfully!");
    }
}
