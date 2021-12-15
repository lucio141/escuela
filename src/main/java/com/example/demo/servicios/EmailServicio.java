package com.example.demo.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String ASUNTO = "Correo de bienvenida";
    private static final String TEXTO = "Bienvenido a nuestra página. Gracias por registrarte";

    @Async
    public void enviar (String to){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(to);
        mensaje.setFrom(from);
        mensaje.setSubject(ASUNTO);
        mensaje.setText(TEXTO);
        sender.send(mensaje);
    }
}
