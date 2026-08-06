package com.maquillaje.maquillajebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @GetMapping("/usuario")
    public String usuario() {
        return "Bienvenido Usuario";
    }
}
