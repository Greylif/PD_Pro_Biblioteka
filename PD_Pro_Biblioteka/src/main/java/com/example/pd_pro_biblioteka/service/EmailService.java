package com.example.pd_pro_biblioteka.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        mailSender.send(message);
    }

    @Scheduled(cron = "0 30 17 * * ?")
    public void scheduledEmail() throws MessagingException {
        System.out.println("Email scheduled");
        if (1 == 1) {
            sendEmail("s092677@student.tu.kielce.pl", "Codzienny Raport", "To jest automatyczna wiadomość.");
        }
    }
}
