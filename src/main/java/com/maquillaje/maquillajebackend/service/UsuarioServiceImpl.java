package com.maquillaje.maquillajebackend.service;

import com.maquillaje.maquillajebackend.entity.Rol;
import com.maquillaje.maquillajebackend.entity.Usuario;
import com.maquillaje.maquillajebackend.entity.UsuarioRol;
import com.maquillaje.maquillajebackend.entity.UsuarioRolId;
import com.maquillaje.maquillajebackend.repository.RolRepository;
import com.maquillaje.maquillajebackend.repository.UsuarioRepository;
import com.maquillaje.maquillajebackend.repository.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardar(Usuario usuario) {

        // Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Guardar usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Rol rol;

        // Si el correo es el del administrador, asignar rol Administrador
        if (usuario.getCorreo().equalsIgnoreCase("admin@maquillaje.com")) {

            rol = rolRepository.findByNombre("Administrador")
                    .orElseThrow(() -> new RuntimeException("Rol Administrador no encontrado"));

        } else {

            rol = rolRepository.findByNombre("Usuario")
                    .orElseThrow(() -> new RuntimeException("Rol Usuario no encontrado"));

        }

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(new UsuarioRolId(usuarioGuardado.getId(), rol.getId()));
        usuarioRol.setUsuario(usuarioGuardado);
        usuarioRol.setRol(rol);

        usuarioRolRepository.save(usuarioRol);

        return usuarioGuardado;
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).isPresent();
    }
}