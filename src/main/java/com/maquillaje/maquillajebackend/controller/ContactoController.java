package com.maquillaje.maquillajebackend.controller;

import com.maquillaje.maquillajebackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactoController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String enviar(@RequestBody Map<String, String> datos) {

        emailService.enviarCorreo(
                datos.get("nombre"),
                datos.get("correo"),
                datos.get("mensaje")
        );

        return "Correo enviado correctamente";
    }
}