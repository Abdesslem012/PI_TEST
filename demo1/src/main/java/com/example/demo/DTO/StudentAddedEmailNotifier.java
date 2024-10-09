package com.example.demo.DTO;


import com.example.demo.Entity.Student;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class StudentAddedEmailNotifier {


        private final JavaMailSender javaMailSender;

        @Autowired
        public StudentAddedEmailNotifier(JavaMailSender javaMailSender) {
            this.javaMailSender = javaMailSender;
        }

        @EventListener
        public void sendConfirmationEmail(StudentAddedEvent event) {
            Student student = event.getStudent();
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
                String htmlMsg = "<html><body><h1>Welcome to Our Application!</h1><p>You have been successfully registered as a student.</p></body></html>";
                mimeMessage.setContent(htmlMsg, "text/html");
                helper.setTo(student.getEmail());
                helper.setSubject("Registration Confirmation");
                javaMailSender.send(mimeMessage);
                System.out.println("Confirmation email sent successfully to: " + student.getEmail());
            } catch (MessagingException e) {
                System.out.println("Error sending confirmation email: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


