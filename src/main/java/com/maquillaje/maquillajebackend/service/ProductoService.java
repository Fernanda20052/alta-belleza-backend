package com.maquillaje.maquillajebackend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.maquillaje.maquillajebackend.entity.Producto;
import com.maquillaje.maquillajebackend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private Cloudinary cloudinary;

    // Mostrar solo productos activos
    public List<Producto> obtenerProductos() {

        return productoRepository.findByActivoTrue();

    }

    // Obtener por id
    public Producto obtenerProductoPorId(Long id) {

        return productoRepository.findById(id).orElse(null);

    }

    // Guardar producto con imagen en Cloudinary
    public Producto guardarProducto(

            MultipartFile imagen,

            String nombre,

            String descripcion,

            Double precio,

            Integer stock,

            String categoria

    ) throws IOException {

        Map uploadResult = cloudinary.uploader().upload(
                imagen.getBytes(),
                ObjectUtils.emptyMap()
        );

        String urlImagen = uploadResult.get("secure_url").toString();

        Producto producto = new Producto();

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setImagen(urlImagen);
        producto.setCategoria(categoria);
        producto.setStock(stock);
        producto.setActivo(true);

        return productoRepository.save(producto);

    }

    // Editar
    public Producto actualizarProducto(Long id, Producto productoActualizado) {

        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto == null) {

            return null;

        }

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setStock(productoActualizado.getStock());

        return productoRepository.save(producto);

    }

    // Baja lógica
    public void eliminarProducto(Long id) {

        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {

            producto.setActivo(false);

            productoRepository.save(producto);

        }

    }

}