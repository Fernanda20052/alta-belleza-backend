package com.maquillaje.maquillajebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String nombre, String correo, String mensaje) {

        SimpleMailMessage mail = new SimpleMailMessage();

        // Debe ser el mismo correo configurado en application.properties
        mail.setFrom("fernanda.rodriguez3b@gmail.com");

        // A quién llegará el correo
        mail.setTo("fernanda.rodriguez3b@gmail.com");

        mail.setSubject("Nuevo mensaje desde Alta Belleza");

        mail.setText(
                "Has recibido un nuevo mensaje desde el formulario de contacto.\n\n" +
                        "Nombre: " + nombre +
                        "\nCorreo: " + correo +
                        "\n\nMensaje:\n" + mensaje
        );

        mailSender.send(mail);
    }
}