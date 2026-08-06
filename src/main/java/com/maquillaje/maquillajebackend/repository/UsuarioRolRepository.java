package com.maquillaje.maquillajebackend.repository;

import com.maquillaje.maquillajebackend.entity.UsuarioRol;
import com.maquillaje.maquillajebackend.entity.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {

    List<UsuarioRol> findByUsuarioId(Integer usuarioId);

}