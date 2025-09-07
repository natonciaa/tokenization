package co.com.tokenization.tokenization_api.application.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("tokenizationapi@gmail.com");
        mensaje.setTo(to);
        mensaje.setSubject(subject);
        mensaje.setText(text);
        mailSender.send(mensaje);
    }
}
