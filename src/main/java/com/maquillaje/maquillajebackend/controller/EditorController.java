package com.maquillaje.maquillajebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditorController {

    @GetMapping("/editor")
    public String editor() {
        return "Bienvenido Editor";
    }
}
