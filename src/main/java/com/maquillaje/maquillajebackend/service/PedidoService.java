package com.maquillaje.maquillajebackend.service;

import com.maquillaje.maquillajebackend.dto.PedidoRequest;
import com.maquillaje.maquillajebackend.dto.ProductoPedidoRequest;
import com.maquillaje.maquillajebackend.entity.DetallePedido;
import com.maquillaje.maquillajebackend.entity.Pedido;
import com.maquillaje.maquillajebackend.entity.Producto;
import com.maquillaje.maquillajebackend.repository.DetallePedidoRepository;
import com.maquillaje.maquillajebackend.repository.PedidoRepository;
import com.maquillaje.maquillajebackend.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los pedidos
    public List<Pedido> obtenerPedidos() {

        return pedidoRepository.findAll();

    }

    // Obtener pedidos de un usuario
    public List<Pedido> obtenerPedidosPorUsuario(String usuario) {

        return pedidoRepository.findByUsuarioOrderByFechaDesc(usuario);

    }

    // Guardar pedido
    @Transactional
    public Pedido guardarPedido(PedidoRequest request) {

        Pedido pedido = new Pedido();

        pedido.setUsuario(request.getUsuario());
        pedido.setNombreCliente(request.getNombreCliente());
        pedido.setTelefono(request.getTelefono());
        pedido.setDireccion(request.getDireccion());
        pedido.setCiudad(request.getCiudad());
        pedido.setCodigoPostal(request.getCodigoPostal());

        pedido.setTotal(request.getTotal());

        pedido.setEstado("PAGADO");

        pedido.setFecha(LocalDateTime.now());

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        for (ProductoPedidoRequest item : request.getProductos()) {

            Producto producto = productoRepository
                    .findById(item.getId())
                    .orElseThrow(() ->
                            new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {

                throw new RuntimeException(
                        "No hay stock suficiente para " + producto.getNombre()
                );

            }

            DetallePedido detalle = new DetallePedido();

            detalle.setPedido(pedidoGuardado);

            detalle.setProducto(producto);

            detalle.setNombreProducto(producto.getNombre());

            detalle.setPrecio(producto.getPrecio());

            detalle.setCantidad(item.getCantidad());

            detalle.setSubtotal(
                    producto.getPrecio() * item.getCantidad()
            );

            detallePedidoRepository.save(detalle);

            producto.setStock(
                    producto.getStock() - item.getCantidad()
            );

            productoRepository.save(producto);

        }

        return pedidoGuardado;

    }
    // Obtener pedidos con sus productos
    public List<Map<String, Object>> obtenerPedidosConProductos(String usuario) {

        List<Pedido> pedidos =
                pedidoRepository.findByUsuarioOrderByFechaDesc(usuario);

        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            Map<String, Object> pedidoMap = new HashMap<>();

            pedidoMap.put("id", pedido.getId());
            pedidoMap.put("fecha", pedido.getFecha());
            pedidoMap.put("estado", pedido.getEstado());
            pedidoMap.put("total", pedido.getTotal());

            List<DetallePedido> detalles =
                    detallePedidoRepository.findByPedidoId(pedido.getId());

            pedidoMap.put("productos", detalles);

            respuesta.add(pedidoMap);

        }

        return respuesta;

    }

}