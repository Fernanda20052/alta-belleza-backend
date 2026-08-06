package com.maquillaje.maquillajebackend.repository;

import com.maquillaje.maquillajebackend.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuario(String usuario);

    boolean existsByUsuarioAndProducto_Id(String usuario, Long productoId);

    @Modifying
    @Transactional
    void deleteByUsuarioAndProducto_Id(String usuario, Long productoId);

}