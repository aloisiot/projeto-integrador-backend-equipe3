package dh.projetointegradorctd.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String text, String subject, @Email String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text);
        message.setTo(to);
        mailSender.send(message);
    }
}
