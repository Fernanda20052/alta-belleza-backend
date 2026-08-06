package com.maquillaje.maquillajebackend.service;

import com.maquillaje.maquillajebackend.entity.Favorito;
import com.maquillaje.maquillajebackend.entity.Producto;
import com.maquillaje.maquillajebackend.repository.FavoritoRepository;
import com.maquillaje.maquillajebackend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener favoritos de un usuario
    public List<Favorito> obtenerFavoritos(String usuario) {

        return favoritoRepository.findByUsuario(usuario);

    }

    // Agregar un favorito
    public Favorito agregarFavorito(String usuario, Long productoId) {

        if (favoritoRepository.existsByUsuarioAndProducto_Id(usuario, productoId)) {

            return null;

        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Favorito favorito = new Favorito();

        favorito.setUsuario(usuario);

        favorito.setProducto(producto);

        return favoritoRepository.save(favorito);

    }

    // Eliminar un favorito
    @Transactional
    public void eliminarFavorito(String usuario, Long productoId) {

        favoritoRepository.deleteByUsuarioAndProducto_Id(usuario, productoId);

    }

}