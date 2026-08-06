package com.maquillaje.maquillajebackend.controller;

import com.maquillaje.maquillajebackend.entity.Favorito;
import com.maquillaje.maquillajebackend.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/{usuario}")
    public List<Favorito> obtenerFavoritos(
            @PathVariable String usuario
    ) {

        return favoritoService.obtenerFavoritos(usuario);

    }

    @PostMapping("/{usuario}/{productoId}")
    public Favorito agregarFavorito(
            @PathVariable String usuario,
            @PathVariable Long productoId
    ) {

        return favoritoService.agregarFavorito(usuario, productoId);

    }

    @DeleteMapping("/{usuario}/{productoId}")
    public void eliminarFavorito(
            @PathVariable String usuario,
            @PathVariable Long productoId
    ) {

        favoritoService.eliminarFavorito(usuario, productoId);

    }

}