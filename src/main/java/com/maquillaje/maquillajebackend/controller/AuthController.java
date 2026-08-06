package com.maquillaje.maquillajebackend.controller;

import com.maquillaje.maquillajebackend.dto.LoginRequest;
import com.maquillaje.maquillajebackend.dto.RegistroRequest;
import com.maquillaje.maquillajebackend.entity.Usuario;
import com.maquillaje.maquillajebackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public String registro(@RequestBody RegistroRequest request) {

        if (usuarioService.existeCorreo(request.getCorreo())) {
            return "El correo ya está registrado";
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena());

        // Todos los usuarios registrados serán CLIENTE
        usuario.setRol("CLIENTE");

        usuarioService.guardar(usuario);

        return "Usuario registrado correctamente";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.buscarPorCorreo(request.getCorreo());

        if (usuario == null) {
            return "Usuario no encontrado";
        }

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            return "Contraseña incorrecta";
        }

        return "Inicio de sesión correcto";
    }

    @PostMapping("/logout")
    public String logout() {
        return "Sesión cerrada";
    }

}