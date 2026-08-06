package com.maquillaje.maquillajebackend.controller;

import com.maquillaje.maquillajebackend.entity.Producto;
import com.maquillaje.maquillajebackend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://alta-belleza-frontend.vercel.app"
})
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Mostrar productos
    @GetMapping
    public List<Producto> obtenerProductos() {

        return productoService.obtenerProductos();

    }

    // Agregar producto con imagen
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Producto guardarProducto(

            @RequestParam("imagen") MultipartFile imagen,

            @RequestParam("nombre") String nombre,

            @RequestParam("descripcion") String descripcion,

            @RequestParam("precio") Double precio,

            @RequestParam("stock") Integer stock,

            @RequestParam("categoria") String categoria

    ) throws IOException {

        return productoService.guardarProducto(

                imagen,

                nombre,

                descripcion,

                precio,

                stock,

                categoria

        );

    }

    // Editar producto
    @PutMapping("/{id}")
    public Producto actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto producto
    ) {

        return productoService.actualizarProducto(id, producto);

    }

    // Baja lógica
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {

        productoService.eliminarProducto(id);

    }

}