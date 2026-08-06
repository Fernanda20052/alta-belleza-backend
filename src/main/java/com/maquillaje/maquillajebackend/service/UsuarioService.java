package com.maquillaje.maquillajebackend.service;

import com.maquillaje.maquillajebackend.entity.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario guardar(Usuario usuario);

    Usuario buscarPorCorreo(String correo);

    List<Usuario> listar();

    boolean existeCorreo(String correo);
}
