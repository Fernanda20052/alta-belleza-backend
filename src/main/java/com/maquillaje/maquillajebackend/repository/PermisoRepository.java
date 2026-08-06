package com.maquillaje.maquillajebackend.repository;

import com.maquillaje.maquillajebackend.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    Optional<Permiso> findByNombre(String nombre);

}
