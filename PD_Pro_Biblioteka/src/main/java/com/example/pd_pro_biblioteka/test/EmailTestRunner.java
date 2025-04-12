package com.example.pd_pro_biblioteka.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import com.example.pd_pro_biblioteka.service.EmailService;

@Component
public class EmailTestRunner implements CommandLineRunner {

    private final EmailService emailService;

    public EmailTestRunner(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {
       /* try {
            emailService.sendEmail("s092677@student.tu.kielce.pl", "Testowy Email", "To jest testowa wiadomość wysyłana po starcie aplikacji.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        */

    }


}
